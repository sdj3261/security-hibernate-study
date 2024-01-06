package com.example.demo.controllers;

import com.example.demo.infra.UserDetailsDao;
import com.example.demo.utils.AccessTokenGenerator;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserDetailsDao userDetailsDao;
    private final AccessTokenGenerator accessTokenGenerator;

    public LoginService(UserDetailsDao userDetailsDao, AccessTokenGenerator accessTokenGenerator) {
        this.userDetailsDao = userDetailsDao;
        this.accessTokenGenerator = accessTokenGenerator;
    }


    public String login(String username, String password) {
        return userDetailsDao.findByUsername(username)
                .filter(userDetails -> password.equals("password"))
                .map(userDetails -> {
                    String id = userDetails.getUsername();
                    String accessToken = accessTokenGenerator.generate(id);
                    userDetailsDao.addAccessToken(id, accessToken);
                    return accessToken;
                })
                .orElseThrow(() -> new BadCredentialsException("Login failed"));
    }
}
