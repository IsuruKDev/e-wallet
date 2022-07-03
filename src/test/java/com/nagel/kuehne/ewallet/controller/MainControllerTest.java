package com.nagel.kuehne.ewallet.controller;

import com.nagel.kuehne.ewallet.security.jwt.JwtUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void allAccess() throws Exception {

        mockMvc.perform(
                        get("/api/access/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Public Content")));
    }

    @Test @WithMockUser(roles = {"USER","ADMIN"})
    void userAccess() throws Exception {
        mockMvc.perform(
                        get("/api/access/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User Content")));
    }

    @Test @WithMockUser(roles = {"ADMIN"})
    void adminAccess() throws Exception {
        mockMvc.perform(
                        get("/api/access/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Admin Content")));
    }
}