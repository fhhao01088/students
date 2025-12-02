package com.company.asset.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
public class AuthFilter extends OncePerRequestFilter {

    private static final Set<String> ALLOWLIST = Set.of("/api/auth/login");
    private static final Set<String> WRITE_METHODS = Set.of("POST", "PUT", "PATCH", "DELETE");

    private final AuthService authService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        if (!path.startsWith("/api/")) {
            return true;
        }
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        return ALLOWLIST.contains(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            writeError(response, HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String token = authorization.substring(7).trim();
        Optional<AuthenticatedUser> maybeUser = authService.authenticate(token);
        if (maybeUser.isEmpty()) {
            writeError(response, HttpStatus.UNAUTHORIZED, "Token is invalid or expired");
            return;
        }

        AuthenticatedUser user = maybeUser.get();
        AuthContextHolder.set(user);
        try {
            if (isUsersApi(request) && !user.getRole().isAdmin()) {
                writeError(response, HttpStatus.FORBIDDEN, "Administrator privileges required");
                return;
            }
            if (isAssetsApi(request)) {
                if (!user.getRole().canReadAssets()) {
                    writeError(response, HttpStatus.FORBIDDEN, "Insufficient permissions to read assets");
                    return;
                }
                if (isWriteMethod(request) && !user.getRole().canWriteAssets()) {
                    writeError(response, HttpStatus.FORBIDDEN, "Insufficient permissions to modify assets");
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } finally {
            AuthContextHolder.clear();
        }
    }

    private boolean isUsersApi(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/users") || path.startsWith("/api/users/");
    }

    private boolean isAssetsApi(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/assets") || path.startsWith("/api/assets/");
    }

    private boolean isWriteMethod(HttpServletRequest request) {
        return WRITE_METHODS.contains(request.getMethod().toUpperCase());
    }

    private void writeError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String payload = serialize(Map.of("message", message));
        response.getWriter().write(payload);
    }

    private String serialize(Map<String, String> payload) throws JsonProcessingException {
        return objectMapper.writeValueAsString(payload);
    }
}
