package com.stock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.controller.request.LoginRequest;
import com.stock.controller.response.LoginResponse;
import com.stock.modal.Status;
import com.stock.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Mockito.reset(loginService);
    }

    @Test
    void testLoginUser() throws Exception {
        // Prepare request and response objects
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("testPassword");

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setStatus(Status.SUCCESS);

        // Mock the service method
        when(loginService.loginUser(any(LoginRequest.class))).thenReturn(loginResponse);

        // Perform the POST request and validate the response
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));

        // Verify the interaction with the mocked service
        Mockito.verify(loginService, Mockito.times(1)).loginUser(any(LoginRequest.class));
    }
}