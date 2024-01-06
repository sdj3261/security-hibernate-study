package com.example.demo.controllers;

import com.example.demo.dto.SignupRequestDto;
import com.example.demo.dto.SignupResultDto;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.service.SignupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final SignupService signupService;

    public UserController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResultDto signup(
            @Valid @RequestBody SignupRequestDto signupRequestDto) {
        String accessToken = signupService.signup(
                signupRequestDto.username().trim(),
                signupRequestDto.password().trim());

        return new SignupResultDto(accessToken);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String alreadyExists() {
        return "User Already Exists";
    }
}
