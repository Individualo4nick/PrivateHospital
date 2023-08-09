package com.example.authorization.domain.entity.Enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    STAFF,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
    public static String[] getRoles(){
        return new String[]{String.valueOf(STAFF), String.valueOf(USER)};
    }
}
