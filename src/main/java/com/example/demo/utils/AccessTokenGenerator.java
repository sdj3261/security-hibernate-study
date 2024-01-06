package com.example.demo.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class AccessTokenGenerator {
    public String generate(String userId) {
        LocalDateTime now = LocalDateTime.now();
        return userId + "." + now.toEpochSecond(ZoneOffset.UTC);
    }
}
