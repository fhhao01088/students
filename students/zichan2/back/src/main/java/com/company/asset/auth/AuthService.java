package com.company.asset.auth;

import java.time.Duration;
import java.util.Locale;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private static final Duration TOKEN_TTL = Duration.ofHours(2);

    private final UserRepository userRepository;
    private final InMemoryTokenStore tokenStore;

    public AuthService(UserRepository userRepository, InMemoryTokenStore tokenStore) {
        this.userRepository = userRepository;
        this.tokenStore = tokenStore;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(String username, String password) {
        UserAccount user = userRepository.findByUsername(username)
                .orElseThrow(this::unauthorized);
        if (!user.getPassword().equals(password)) {
            throw unauthorized();
        }
        if (isDisabled(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User account is disabled");
        }
        String token = tokenStore.store(user.getId(), user.getRole().getCode(), TOKEN_TTL);
        return new LoginResponse(token, UserDto.fromEntity(user));
    }

    @Transactional(readOnly = true)
    public Optional<AuthenticatedUser> authenticate(String token) {
        Optional<InMemoryTokenStore.TokenDetails> optional = tokenStore.get(token);
        if (optional.isEmpty()) {
            return Optional.empty();
        }

        InMemoryTokenStore.TokenDetails details = optional.get();
        Optional<UserAccount> userOptional = userRepository.findById(details.userId());
        if (userOptional.isEmpty()) {
            tokenStore.remove(token);
            return Optional.empty();
        }

        UserAccount user = userOptional.get();
        if (isDisabled(user)) {
            tokenStore.remove(token);
            return Optional.empty();
        }

        RoleCode currentRole = user.getRole().getCode();
        if (currentRole != details.role()) {
            tokenStore.updateRole(token, currentRole);
        }

        return Optional.of(AuthenticatedUser.from(user));
    }

    public void logout(String token) {
        tokenStore.remove(token);
    }

    private ResponseStatusException unauthorized() {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }

    private boolean isDisabled(UserAccount user) {
        return user.getStatus() != null && user.getStatus().toUpperCase(Locale.ROOT).equals("DISABLED");
    }
}
