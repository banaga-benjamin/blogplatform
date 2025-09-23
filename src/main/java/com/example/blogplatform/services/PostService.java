package com.example.blogplatform.services;

import com.example.blogplatform.dtos.PostDTO;
import com.example.blogplatform.dtos.CreatePostRequest;

public interface PostService {
    public PostDTO createPost(CreatePostRequest request);
}
