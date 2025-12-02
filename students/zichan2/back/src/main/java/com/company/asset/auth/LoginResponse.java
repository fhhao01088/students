package com.company.asset.auth;

public record LoginResponse(String token, UserDto user) {
}
