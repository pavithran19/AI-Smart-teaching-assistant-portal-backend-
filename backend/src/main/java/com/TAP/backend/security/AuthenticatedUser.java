package com.TAP.backend.security;

import lombok.Getter;

@Getter
public class AuthenticatedUser {

    private final String email;
    private final Long userId;
    private final Long collegeId;
    private final String role;

    public AuthenticatedUser(String email, Long userId, Long collegeId, String role) {
        this.email = email;
        this.userId = userId;
        this.collegeId = collegeId;
        this.role = role;
    }
}