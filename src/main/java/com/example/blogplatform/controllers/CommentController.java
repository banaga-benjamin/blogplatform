package com.example.blogplatform.controllers;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public ResponseEntity<CommentDTO> createComment(@RequestParam Long pid, @RequestBody @Valid CommentRequest request) {
        CommentDTO commentdto = service.createComment(pid, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentdto);
    }
    
    @PutMapping("/{cid}")
    public ResponseEntity<String> updateComment(@PathVariable Long cid, @RequestParam Long pid, @RequestBody @Valid CommentRequest request) {
        service.updateComment(cid, pid, request);
        return ResponseEntity.ok("successfully updated comment");
    }

    @DeleteMapping("/{cid}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long cid, @RequestParam Long pid) {
        service.deleteComment(cid, pid);
        return ResponseEntity.noContent( ).build( );
    }

    @GetMapping("/{cid}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long cid, @RequestParam Long pid) {
        return ResponseEntity.ok(service.getComment(cid, pid));
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments(@RequestParam Long pid) {
        return ResponseEntity.ok(service.getComments(pid));
    }
}
