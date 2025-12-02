package com.company.asset.auth;

public enum RoleCode {
    ADMIN,
    ASSET_ADMIN,
    USER;

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public boolean canReadAssets() {
        return this == ADMIN || this == ASSET_ADMIN || this == USER;
    }

    public boolean canWriteAssets() {
        return this == ADMIN || this == ASSET_ADMIN;
    }
}
