package com.example.blogplatform.services;

import com.example.blogplatform.dtos.PostDTO;
import com.example.blogplatform.dtos.PostRequest;

public interface PostService {
    public PostDTO createPost(PostRequest request);
    public void updatePost(Long id, PostRequest request);
}
