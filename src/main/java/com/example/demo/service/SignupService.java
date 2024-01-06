package com.example.demo.service;

import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.infra.UserDetailsDao;
import com.example.demo.utils.AccessTokenGenerator;
import io.hypersistence.tsid.TSID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SignupService {
    private final AccessTokenGenerator accessTokenGenerator;

    private final PasswordEncoder passwordEncoder;

    private final UserDetailsDao userDetailsDao;

    public SignupService(AccessTokenGenerator accessTokenGenerator,
                         PasswordEncoder passwordEncoder,
                         UserDetailsDao userDetailsDao) {
        this.accessTokenGenerator = accessTokenGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsDao = userDetailsDao;
    }

    public String signup(String username, String password) {
        if (userDetailsDao.existsByUsername(username)) {
            throw new UserAlreadyExistsException();
        }

        String id = TSID.Factory.getTsid().toString();
        String encodedPassword = passwordEncoder.encode(password);
        String accessToken = accessTokenGenerator.generate(id);

        userDetailsDao.addUser(id, username, encodedPassword);
        userDetailsDao.addAccessToken(id, accessToken);

        return accessToken;
    }
}
