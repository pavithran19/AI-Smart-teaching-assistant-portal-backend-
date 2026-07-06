package com.TAP.backend.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static AuthenticatedUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof AuthenticatedUser)) {
            throw new IllegalStateException("No authenticated user found in security context");
        }
        return (AuthenticatedUser) principal;
    }

    public static Long getCurrentUserCollegeId() {
        return getCurrentUser().getCollegeId();
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public static String getCurrentUserRole() {
        return getCurrentUser().getRole();
    }
}