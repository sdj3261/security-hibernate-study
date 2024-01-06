package com.example.demo.controllers;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResultDto;
import com.example.demo.security.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {
    private final LoginService loginService;
    private final LogoutService logoutService;

    @Autowired
    public SessionController(LoginService loginService, LogoutService logoutService) {
        this.loginService = loginService;
        this.logoutService = logoutService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(@RequestBody LoginRequestDto loginRequestDto) {
        String accessToken = loginService.login(loginRequestDto.username(), loginRequestDto.password());
        return new LoginResultDto(accessToken);
    }

    @DeleteMapping
    public String logout(Authentication authentication) {
        String accessToken = authentication.getCredentials().toString();

        logoutService.logout(accessToken);

        return "Logout";
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed() {
        return "Bad Request";
    }
}
