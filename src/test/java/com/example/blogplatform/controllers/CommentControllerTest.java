package com.example.blogplatform.controllers;

import org.springframework.http.MediaType;
import com.example.blogplatform.dtos.AuthRequest;
import com.example.blogplatform.dtos.AuthResponse;
import com.example.blogplatform.dtos.CommentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

    private String token;

    @BeforeEach
    private void setUp( ) throws Exception {
        AuthRequest request = new AuthRequest( );
        request.setUsername("testuser");
        request.setPassword("testpassword");

        mock.perform(post("/apis/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
            .andExpect(status( ).isOk( ));

        MvcResult result = mock.perform(post("/apis/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
            .andExpect(status( ).isOk( ))
            .andExpect(jsonPath("$.token").exists( ))
            .andReturn( );

        String result_json = result.getResponse( ).getContentAsString( );
        AuthResponse response = mapper.readValue(result_json, AuthResponse.class);
        token = response.getToken( );
    }

    @Test
    private void createComment( ) throws Exception {
        CommentRequest request = new CommentRequest( );
        request.setContent("example comment content");
        String request_json = mapper.writeValueAsString(request);

        mock.perform(post("apis/comment")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example comment content"))
            .andExpect(status( ).isCreated( ));
    }
}
