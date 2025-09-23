package com.example.blogplatform.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.blogplatform.dtos.PostDTO;
import com.example.blogplatform.dtos.PostRequest;
import com.example.blogplatform.services.PostService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/apis/post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostRequest request) {
        PostDTO postdto = service.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(postdto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody @Valid PostRequest request) {
        service.updatePost(id, request);
        return ResponseEntity.ok("succesffully updated post");
    }

}
