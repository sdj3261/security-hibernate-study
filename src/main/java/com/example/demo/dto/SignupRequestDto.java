package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record SignupRequestDto(
        @NotBlank String username, @NotBlank String password) {
}
