package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class AccessTokenAuthentificationFilter extends OncePerRequestFilter {
    private final String AUTHORIZATION_PREFIX = "Bearer ";
    private final AccessTokenService accessTokenService;

    public AccessTokenAuthentificationFilter(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //TODO: 인증작업 추가
        String accessToken = parseAccessToken(request);
        Authentication authentication = accessTokenService.authenticate(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }


    private String parseAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(authorization -> authorization.startsWith(AUTHORIZATION_PREFIX))
                .map(authorization -> authorization.substring((AUTHORIZATION_PREFIX).length()))
                .orElse("");
    }
}
