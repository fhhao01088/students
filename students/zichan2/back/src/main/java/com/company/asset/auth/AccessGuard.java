package com.company.asset.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class AccessGuard {

    private AccessGuard() {
    }

    public static void requireAdmin() {
        AuthenticatedUser user = AuthContextHolder.requireUser();
        if (!user.getRole().isAdmin()) {
            throw forbidden("Administrator privileges required");
        }
    }

    public static void requireAssetRead() {
        AuthenticatedUser user = AuthContextHolder.requireUser();
        if (!user.getRole().canReadAssets()) {
            throw forbidden("Insufficient permissions to read assets");
        }
    }

    public static void requireAssetWrite() {
        AuthenticatedUser user = AuthContextHolder.requireUser();
        if (!user.getRole().canWriteAssets()) {
            throw forbidden("Insufficient permissions to modify assets");
        }
    }

    private static ResponseStatusException forbidden(String message) {
        return new ResponseStatusException(HttpStatus.FORBIDDEN, message);
    }
}
