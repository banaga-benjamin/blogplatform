package com.example.blogplatform.controllers;

import org.junit.jupiter.api.Test;
import jakarta.transaction.Transactional;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import com.example.blogplatform.dtos.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.JsonProcessingException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    // registration tests

    @Test
    void registerNewUser( ) throws JsonProcessingException, Exception {
        AuthRequest request = new AuthRequest( );
        request.setUsername("testuser");
        request.setPassword("testpassword");

        mock.perform(post("/apis/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
            .andExpect(status( ).isOk( ));
    }

    @Test
    void registerDuplicateUser( ) throws JsonProcessingException, Exception {
        AuthRequest request = new AuthRequest( );
        request.setUsername("testuser");
        request.setPassword("testpassword");

        mock.perform(post("/apis/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
            .andExpect(status( ).isOk( ));

        request.setPassword("anypassword");
        mock.perform(post("/apis/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
            .andExpect(status( ).isBadRequest( ));
    }

    // login tests

    @Test
    void loginValidUser( ) throws JsonProcessingException, Exception {
        AuthRequest request = new AuthRequest( );
        request.setUsername("testuser");
        request.setPassword("testpassword");

        mock.perform(post("/apis/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
            .andExpect(status( ).isOk( ));

        mock.perform(post("/apis/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
            .andExpect(status( ).isOk( ))
            .andExpect(jsonPath("$.token").exists( ));
    }

    @Test
    void loginInvalidUser( ) throws JsonProcessingException, Exception {
        AuthRequest request = new AuthRequest( );
        request.setUsername("testuser");
        request.setPassword("testpassword");

        mock.perform(post("/apis/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
            .andExpect(status( ).isNotFound( ));
    }

    @Test
    void loginInvalidPassword( ) throws JsonProcessingException, Exception {
        AuthRequest request = new AuthRequest( );
        request.setUsername("testuser");
        request.setPassword("testpassword");

        mock.perform(post("/apis/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
            .andExpect(status( ).isOk( ));

        request.setPassword(" wrongpassword");
        mock.perform(post("/apis/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
            .andExpect(status( ).isNotFound( ));
    }
}
