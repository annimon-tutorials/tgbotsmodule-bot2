package com.example.secondbot.roles;

import java.util.EnumSet;

public enum UserRole {
    IGNORED,
    REGULAR;

    public static EnumSet<UserRole> all() {
        return EnumSet.allOf(UserRole.class);
    }
}
