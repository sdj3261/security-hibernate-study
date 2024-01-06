package com.example.demo.service;

import com.example.demo.infra.UserDetailsDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogoutService {
    private final UserDetailsDao userDetailsDao;

    public LogoutService(UserDetailsDao userDetailsDao) {
        this.userDetailsDao = userDetailsDao;
    }

    public void logout(String accessToken) {
        userDetailsDao.removeAccessToken(accessToken);
    }
}
