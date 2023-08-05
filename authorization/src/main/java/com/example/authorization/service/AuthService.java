package com.example.authorization.service;

import com.example.authorization.config.SecurityConfig;
import com.example.authorization.controller.exc.AuthException;
import com.example.authorization.domain.entity.User;
import com.example.authorization.domain.jwt.JwtAuthentification;
import com.example.authorization.domain.jwt.JwtRequest;
import com.example.authorization.domain.jwt.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = userService.getByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("Неправильный логин"));
        if (SecurityConfig.passwordEncoder().matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getLogin(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        try {
            if (jwtProvider.validateRefreshToken(refreshToken)) {
                final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
                final String login = claims.getSubject();
                final String saveRefreshToken = refreshStorage.get(login);
                if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                    final User user = userService.getByLogin(login).
                            orElseThrow(() -> new AuthException("Пользователь не найден"));
                    final String accessToken = jwtProvider.generateAccessToken(user);
                    return new JwtResponse(accessToken, null);
                }
            }
        }
        catch (ExpiredJwtException expEx){
            throw expEx;
        }
        return new JwtResponse(null, null);
    }

    public JwtAuthentification getAuthInfo() {
        return (JwtAuthentification) SecurityContextHolder.getContext().getAuthentication();
    }

}