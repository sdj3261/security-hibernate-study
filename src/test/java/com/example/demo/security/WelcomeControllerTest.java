package com.example.demo.security;

import com.example.demo.controllers.WelcomeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WelcomeController.class)
public class WelcomeControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET / - with correct Access Token")
    void homeWithAccessToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .header("Authorization", "Bearer TOKEN"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET / - with incorrect Access Token")
    void homeWithIncorrectAccessToken() throws Exception {
        mockMvc.perform(get("/")
                        .header("Authorization", "Bearer XXX"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET / - without Access Token")
    void homeWithoutAccessToken() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isForbidden());
    }

}
