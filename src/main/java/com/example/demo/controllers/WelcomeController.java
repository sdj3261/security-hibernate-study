package com.example.demo.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public String admin() {
        return "Hello, world! admin";
    }

    @GetMapping("/user")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String user() {
        return "Hello, world!";
    }
}
