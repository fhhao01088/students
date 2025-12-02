package com.company.asset.auth;

public record UserDto(
        Long id,
        String username,
        String name,
        RoleCode role,
        String status
) {
    public static UserDto fromEntity(UserAccount user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getRole().getCode(),
                user.getStatus()
        );
    }
}
