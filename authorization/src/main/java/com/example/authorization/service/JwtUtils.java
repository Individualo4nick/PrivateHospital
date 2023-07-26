package com.example.authorization.service;

import com.example.authorization.domain.entity.Enums.Role;
import com.example.authorization.domain.jwt.JwtAuthentification;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentification generate(Claims claims) {
        final JwtAuthentification jwtInfoToken = new JwtAuthentification();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims) {
        System.out.println(claims.toString());
        final List<String> roles = new ArrayList<>();
        final String role = claims.get("roles", String.class);
        roles.add(role);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

}
