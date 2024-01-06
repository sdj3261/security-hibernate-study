package com.example.demo.security;

import com.example.demo.infra.UserDetailsDao;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {
    private final UserDetailsDao userDetailsDao;

    public AccessTokenService(UserDetailsDao userDetailsDao) {
        this.userDetailsDao = userDetailsDao;
    }

    public Authentication authenticate(String accessToken) {
        return userDetailsDao.findByAccessToken(accessToken)
                .map(userDetails ->
                        UsernamePasswordAuthenticationToken.authenticated(
                                userDetails.getUsername(),
                                userDetails.getPassword(),
                                userDetails.getAuthorities()))
                .orElse(null);
    }
}
