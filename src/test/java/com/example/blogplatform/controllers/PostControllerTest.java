package com.example.blogplatform.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.blogplatform.dtos.PostDTO;
import com.example.blogplatform.dtos.PostRequest;
import com.example.blogplatform.dtos.AuthRequest;
import com.example.blogplatform.dtos.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    
    private String token;

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper mapper;

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

    // create post test

    @Test
    public void createUserPost( ) throws Exception {
        PostRequest request = new PostRequest( );
        request.setContent("example content");
        request.setTitle("example title");
        String request_json = mapper.writeValueAsString(request);

        mock.perform(post("/apis/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example content"))
            .andExpect(jsonPath("$.title").value("example title"))
            .andExpect(status( ).isCreated( ));
    }

    // update post test

    @Test
    public void updateUserPost( ) throws Exception {
        PostRequest request = new PostRequest( );
        request.setContent("example content");
        request.setTitle("example title");
        String request_json = mapper.writeValueAsString(request);

        MvcResult result = mock.perform(post("/apis/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example content"))
            .andExpect(jsonPath("$.title").value("example title"))
            .andExpect(status( ).isCreated( ))
            .andReturn( );

        String result_json = result.getResponse( ).getContentAsString( );
        Long id = mapper.readValue(result_json, PostDTO.class).getId( );

        request.setTitle("updated title");
        request.setContent("updated content");
        request_json = mapper.writeValueAsString(request);

        mock.perform(put("/apis/post/{id}", id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(status( ).isOk( ));
    }

    @Test
    public void updateNonExistentPost( ) throws Exception {
        PostRequest request = new PostRequest( );
        request.setContent("example content");
        request.setTitle("example title");
        String request_json = mapper.writeValueAsString(request);

        mock.perform(put("/apis/post/{id}", 9999)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(status( ).isNotFound( ));
    }

    // delete post test
    
    @Test
    public void deleteUserPost( ) throws Exception {
        PostRequest request = new PostRequest( );
        request.setContent("example content");
        request.setTitle("example title");
        String request_json = mapper.writeValueAsString(request);

        MvcResult result = mock.perform(post("/apis/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example content"))
            .andExpect(jsonPath("$.title").value("example title"))
            .andExpect(status( ).isCreated( ))
            .andReturn( );

        String result_json = result.getResponse( ).getContentAsString( );
        Long id = mapper.readValue(result_json, PostDTO.class).getId( );

        mock.perform(delete("/apis/post/{id}", id).header("Authorization", "Bearer " + token))
            .andExpect(status( ).isNoContent( ));
    }

    @Test
    public void deleteNonExistentPost( ) throws Exception {
        mock.perform(delete("/apis/post/{id}", 9999).header("Authorization", "Bearer " + token))
            .andExpect(status( ).isNotFound( ));
    }
    
    // get post tests

    @Test
    public void getUserPost( ) throws Exception {
        PostRequest request = new PostRequest( );
        request.setContent("example content");
        request.setTitle("example title");
        String request_json = mapper.writeValueAsString(request);

        MvcResult result = mock.perform(post("/apis/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example content"))
            .andExpect(jsonPath("$.title").value("example title"))
            .andExpect(status( ).isCreated( ))
            .andReturn( );

        String result_json = result.getResponse( ).getContentAsString( );
        Long id = mapper.readValue(result_json, PostDTO.class).getId( );

        mock.perform(get("/apis/post/{id}", id).header("Authorization", "Bearer " + token))
            .andExpect(status( ).isOk( ));
    }

    @Test
    public void getNonExistentPost( ) throws Exception {
        mock.perform(get("/apis/post/{id}", 9999).header("Authorization", "Bearer " + token))
            .andExpect(status( ).isNotFound( ));
    }

    @Test
    public void getUserPosts( ) throws Exception {
        PostRequest request = new PostRequest( );
        request.setContent("example content");
        request.setTitle("example title");
        String request_json = mapper.writeValueAsString(request);

        mock.perform(post("/apis/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example content"))
            .andExpect(jsonPath("$.title").value("example title"))
            .andExpect(status( ).isCreated( ));

        mock.perform(get("/apis/post").header("Authorization", "Bearer " + token))
            .andExpect(status( ).isOk( ));
    }
}
