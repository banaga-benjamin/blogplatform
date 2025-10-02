package com.example.blogplatform.services;

import com.example.blogplatform.dtos.CommentDTO;
import com.example.blogplatform.dtos.CommentRequest;

public interface CommentService {
    public CommentDTO createComment(CommentRequest request, Long pid);
}
