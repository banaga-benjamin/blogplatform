package com.example.blogplatform.services;

import com.example.blogplatform.dtos.PostDTO;
import com.example.blogplatform.dtos.PostRequest;

public interface PostService {
    public void deletePost(Long id);
    public void updatePost(Long id, PostRequest request);
    
    public PostDTO createPost(PostRequest request);
}
