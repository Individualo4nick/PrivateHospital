package com.example.authorization;

import com.example.authorization.domain.jwt.JwtAuthentification;
import com.example.authorization.domain.jwt.JwtResponse;
import com.example.authorization.service.AuthService;
import com.example.authorization.service.JwtProvider;
import com.example.authorization.service.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtProvider jwtProvider;
    private final AuthService authService;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        if (cookies!= null && getCookieWithTokens(cookies)!=null) {
            String token = getCookieWithTokens(cookies).getValue();
            String accessToken = token.split("--ACCESS--")[1].split("--REFRESH--")[0];
            String refreshToken = token.split("--ACCESS--")[1].split("--REFRESH--")[1];
            if (accessToken != null) {
                try {
                    jwtProvider.validateAccessToken(accessToken);
                }
                catch (ExpiredJwtException expEx){
                    try {
                        JwtResponse jwtResponse = authService.getAccessToken(refreshToken);
                        accessToken = jwtResponse.getAccessToken();
                        Cookie cookie = new Cookie("tokens", "--TYPE--Bearer--ACCESS--"+accessToken+"--REFRESH--"+refreshToken);
                        cookie.setMaxAge(3600);
                        cookie.setHttpOnly(true);
                        HttpServletResponse response1 = (HttpServletResponse) response;
                        response1.addCookie(cookie);
                    }
                    catch (ExpiredJwtException expEx1){
                        chain.doFilter(request, response);
                        return;
                    }
                }
                final Claims claims = jwtProvider.getAccessClaims(accessToken);
                final JwtAuthentification jwtInfoToken = JwtUtils.generate(claims);
                jwtInfoToken.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
            }
        }
        chain.doFilter(request, response);
    }
    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    private Cookie getCookieWithTokens(Cookie[] cookies){
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("tokens")){
                return cookie;
            }
        }
        return null;
    }

}