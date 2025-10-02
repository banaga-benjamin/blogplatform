package com.example.blogplatform.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.blogplatform.dtos.CommentDTO;
import com.example.blogplatform.dtos.CommentRequest;
import com.example.blogplatform.services.CommentService;

@RestController
@RequestMapping("apis/comment")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody @Valid CommentRequest request, @RequestParam Long pid) {
        CommentDTO commentdto = service.createComment(request, pid);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentdto);
    }
    
}
