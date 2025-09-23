package com.example.blogplatform.services;

import java.util.List;

import com.example.blogplatform.dtos.PostDTO;
import com.example.blogplatform.dtos.PostRequest;

public interface PostService {
    public void deletePost(Long id);
    public void updatePost(Long id, PostRequest request);
    
    public PostDTO getPostById(Long id);
    public List<PostDTO> getAllPosts( );
    
    public PostDTO createPost(PostRequest request);
}
