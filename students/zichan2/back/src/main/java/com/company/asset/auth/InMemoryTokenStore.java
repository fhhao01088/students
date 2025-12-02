package com.company.asset.auth;

import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InMemoryTokenStore {

    private final Map<String, TokenDetails> tokens = new ConcurrentHashMap<>();

    public String store(Long userId, RoleCode role, Duration ttl) {
        String token = UUID.randomUUID().toString();
        Instant expiresAt = Instant.now().plus(ttl);
        tokens.put(token, new TokenDetails(userId, role, expiresAt));
        return token;
    }

    public Optional<TokenDetails> get(String token) {
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }
        TokenDetails details = tokens.get(token);
        if (details == null) {
            return Optional.empty();
        }
        if (details.expiresAt().isBefore(Instant.now())) {
            tokens.remove(token);
            return Optional.empty();
        }
        return Optional.of(details);
    }

    public void updateRole(String token, RoleCode role) {
        tokens.computeIfPresent(token, (key, details) -> new TokenDetails(details.userId(), role, details.expiresAt()));
    }

    public void remove(String token) {
        if (token != null) {
            tokens.remove(token);
        }
    }

    @Scheduled(fixedDelay = 120_000)
    public void purgeExpiredTokens() {
        Instant now = Instant.now();
        Iterator<Map.Entry<String, TokenDetails>> iterator = tokens.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, TokenDetails> entry = iterator.next();
            if (entry.getValue().expiresAt().isBefore(now)) {
                iterator.remove();
            }
        }
    }

    public record TokenDetails(Long userId, RoleCode role, Instant expiresAt) {
    }
}
