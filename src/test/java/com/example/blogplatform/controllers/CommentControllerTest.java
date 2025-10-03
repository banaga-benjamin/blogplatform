package com.example.blogplatform.controllers;

import com.example.blogplatform.dtos.PostDTO;
import com.example.blogplatform.dtos.CommentDTO;
import com.example.blogplatform.dtos.PostRequest;
import com.example.blogplatform.dtos.AuthRequest;
import com.example.blogplatform.dtos.AuthResponse;
import com.example.blogplatform.dtos.CommentRequest;

import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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

    // create comment test

    @Test
    public void createComment( ) throws Exception {
        PostRequest postRequest = new PostRequest( );
        postRequest.setContent("example post content");
        postRequest.setTitle("example post title");
        String postrequest_json = mapper.writeValueAsString(postRequest);

        MvcResult postresult = mock.perform(post("/apis/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postrequest_json))
            .andReturn( );
        
        String postresult_json = postresult.getResponse( ).getContentAsString( );
        Long pid = mapper.readValue(postresult_json, PostDTO.class).getId( );

        CommentRequest request = new CommentRequest( );
        request.setContent("example comment content");
        String request_json = mapper.writeValueAsString(request);

        mock.perform(post("/apis/comment")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("pid", String.valueOf(pid))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example comment content"))
            .andExpect(status( ).isCreated( ));
    }

    // update comment test

    @Test
    public void updateComment( ) throws Exception {
        PostRequest postRequest = new PostRequest( );
        postRequest.setContent("example post content");
        postRequest.setTitle("example post title");
        String postrequest_json = mapper.writeValueAsString(postRequest);

        MvcResult postresult = mock.perform(post("/apis/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postrequest_json))
            .andReturn( );
        
        String postresult_json = postresult.getResponse( ).getContentAsString( );
        Long pid = mapper.readValue(postresult_json, PostDTO.class).getId( );

        CommentRequest request = new CommentRequest( );
        request.setContent("example comment content");
        String request_json = mapper.writeValueAsString(request);

        MvcResult result = mock.perform(post("/apis/comment")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("pid", String.valueOf(pid))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example comment content"))
            .andExpect(status( ).isCreated( ))
            .andReturn( );

        String result_json = result.getResponse( ).getContentAsString( );
        Long id = mapper.readValue(result_json, CommentDTO.class).getId( );

        request.setContent("updated comment content");

        mock.perform(put("/apis/comment/{id}", id)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("pid", String.valueOf(pid))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(status( ).isOk( ));
    }

    // delete comment test

    @Test
    public void deleteComment( ) throws Exception {
        PostRequest postRequest = new PostRequest( );
        postRequest.setContent("example post content");
        postRequest.setTitle("example post title");
        String postrequest_json = mapper.writeValueAsString(postRequest);

        MvcResult postresult = mock.perform(post("/apis/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postrequest_json))
            .andReturn( );
        
        String postresult_json = postresult.getResponse( ).getContentAsString( );
        Long pid = mapper.readValue(postresult_json, PostDTO.class).getId( );

        CommentRequest request = new CommentRequest( );
        request.setContent("example comment content");
        String request_json = mapper.writeValueAsString(request);

        MvcResult result = mock.perform(post("/apis/comment")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("pid", String.valueOf(pid))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example comment content"))
            .andExpect(status( ).isCreated( ))
            .andReturn( );

        String result_json = result.getResponse( ).getContentAsString( );
        Long id = mapper.readValue(result_json, CommentDTO.class).getId( );

        mock.perform(delete("/apis/comment/{id}", id)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("pid", String.valueOf(pid))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(status( ).isNoContent( ));
    }

    // get comment tests

    @Test
    public void getUserComment( ) throws Exception {
        PostRequest postRequest = new PostRequest( );
        postRequest.setContent("example post content");
        postRequest.setTitle("example post title");
        String postrequest_json = mapper.writeValueAsString(postRequest);

        MvcResult postresult = mock.perform(post("/apis/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postrequest_json))
            .andReturn( );
        
        String postresult_json = postresult.getResponse( ).getContentAsString( );
        Long pid = mapper.readValue(postresult_json, PostDTO.class).getId( );

        CommentRequest request = new CommentRequest( );
        request.setContent("example comment content");
        String request_json = mapper.writeValueAsString(request);

        MvcResult result = mock.perform(post("/apis/comment")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("pid", String.valueOf(pid))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example comment content"))
            .andExpect(status( ).isCreated( ))
            .andReturn( );

        String result_json = result.getResponse( ).getContentAsString( );
        Long cid = mapper.readValue(result_json, CommentDTO.class).getId( );

        mock.perform(get("/apis/comment/{id}", cid)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("pid", String.valueOf(pid)))
            .andExpect(jsonPath("$.content").value("example comment content"))
            .andExpect(status( ).isOk( ));
    }

    @Test
    public void getUserComments( ) throws Exception {
        PostRequest postRequest = new PostRequest( );
        postRequest.setContent("example post content");
        postRequest.setTitle("example post title");
        String postrequest_json = mapper.writeValueAsString(postRequest);

        MvcResult postresult = mock.perform(post("/apis/post")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postrequest_json))
            .andReturn( );
        
        String postresult_json = postresult.getResponse( ).getContentAsString( );
        Long pid = mapper.readValue(postresult_json, PostDTO.class).getId( );

        CommentRequest request = new CommentRequest( );
        request.setContent("example comment content");
        String request_json = mapper.writeValueAsString(request);

        mock.perform(post("/apis/comment")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("pid", String.valueOf(pid))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request_json))
            .andExpect(jsonPath("$.content").value("example comment content"))
            .andExpect(status( ).isCreated( ));

        mock.perform(get("/apis/comment")
                        .header("Authorization", "Bearer " + token)
                        .queryParam("pid", String.valueOf(pid)))
            .andExpect(status( ).isOk( ));
    }
}
