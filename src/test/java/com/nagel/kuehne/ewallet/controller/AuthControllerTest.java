package com.nagel.kuehne.ewallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagel.kuehne.ewallet.payload.LoginRequest;
import com.nagel.kuehne.ewallet.payload.SignUpRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private SignUpRequest signUpRequest;
    private LoginRequest loginRequest;


    @BeforeEach
    void setUp() {
        signUpRequest = SignUpRequest
                .builder()
                .username("testuser6")
                .password("Stl@123456")
                .role(new HashSet<>(Arrays.asList("user")))
                .build();

        loginRequest = LoginRequest
                .builder()
                .username("testuser6")
                .password("Stl@123456")
                .build();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerUserTest() throws Exception {

        mockMvc.perform(
                        post("/api/auth/signup")
                                .content(asJsonString(signUpRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User registered successfully!")));

    }

    @Test
    void registerUserAtExistTest() throws Exception {

        SignUpRequest existUser = SignUpRequest
                .builder()
                .username("testuser6")
                .password("Stl@123456")
                .role(new HashSet<>(Arrays.asList("user")))
                .build();

        mockMvc.perform(
                        post("/api/auth/signup")
                                .content(asJsonString(existUser))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("username {"+existUser.getUsername()+"} is already taken")));

    }

    @Test
    void givenNotFoundOnRoleIsEmptyTest() throws Exception {

        SignUpRequest roleEmptyUser = SignUpRequest
                .builder()
                .username("david123")
                .password("Stl@123456")
                .role(null)
                .build();

        mockMvc.perform(
                        post("/api/auth/signup")
                                .content(asJsonString(roleEmptyUser))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RuntimeException));
            //    .andExpect(result -> assertEquals("Error: Role is not found",
            //            result.getResolvedException().getMessage()));
    }

    @Test
    void authenticateUserTest() throws Exception {

        mockMvc.perform(
                        post("/api/auth/signin")
                                .content(asJsonString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 14,\n" +
                        "    \"username\": \"testuser6\"}"));

    }


    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}