package com.example.blogplatform.services;

import com.example.blogplatform.dtos.CommentDTO;
import com.example.blogplatform.dtos.CommentRequest;

public interface CommentService {
    public CommentDTO createComment(Long pid, CommentRequest request);
    public void updateComment(Long cid, Long pid, CommentRequest request);
    public void deleteComment(Long cid, Long pid);
}
