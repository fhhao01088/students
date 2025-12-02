package com.company.asset.auth;

public class AuthenticatedUser {

    private final Long id;
    private final String username;
    private final String name;
    private final String status;
    private final RoleCode role;

    public AuthenticatedUser(Long id, String username, String name, String status, RoleCode role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.status = status;
        this.role = role;
    }

    public static AuthenticatedUser from(UserAccount user) {
        return new AuthenticatedUser(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getStatus(),
                user.getRole().getCode()
        );
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public RoleCode getRole() {
        return role;
    }

    public UserDto toDto() {
        return new UserDto(id, username, name, role, status);
    }
}
