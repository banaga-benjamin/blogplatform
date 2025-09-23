package com.example.blogplatform.services;

import org.springframework.stereotype.Service;

import com.example.blogplatform.models.Post;
import com.example.blogplatform.models.User;
import com.example.blogplatform.dtos.PostDTO;
import com.example.blogplatform.dtos.PostRequest;

import com.example.blogplatform.repositories.PostRepository;
import com.example.blogplatform.repositories.UserRepository;

import com.example.blogplatform.exceptions.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class PostServiceImplementation implements PostService {

    private final PostRepository postrepo;
    private final UserRepository userrepo;

    public PostServiceImplementation(PostRepository postrepo, UserRepository userrepo) { 
        this.postrepo = postrepo;
        this.userrepo = userrepo;
    }

    private User getCurrentUser( ) {
        String username = SecurityContextHolder.getContext( ).getAuthentication( ).getName( );
        User user = userrepo.findByUsername(username).orElseThrow(
            ( ) -> new ResourceNotFoundException("user does not exist"));
        return user;
    }

    private PostDTO mapToPostDTO(Post post) {
        PostDTO postdto = new PostDTO( );

        postdto.setId(post.getId( ));
        postdto.setTitle(post.getTitle( ));
        postdto.setContent(post.getContent( ));

        return postdto;
    }

    @Override
    public PostDTO createPost(PostRequest request) {
        Post post = new Post( );

        post.setUser(getCurrentUser( ));
        post.setTitle(request.getTitle( ));
        post.setContent(request.getContent());

        return mapToPostDTO(postrepo.save(post));
    }
    
    @Override
    public void updatePost(Long id, PostRequest request) {
        Post post = postrepo.findById(id).orElseThrow(
            ( ) -> new ResourceNotFoundException("post does not exist"));
        
        if (post.getUser( ) != getCurrentUser( )) {
            throw new ResourceNotFoundException("post does not exist");
        }

        post.setContent(request.getContent( ));
        post.setTitle(request.getTitle( ));
    }
    
    @Override
    public void deletePost(Long id) {
        Post post = postrepo.findById(id).orElseThrow(
            ( ) -> new ResourceNotFoundException("post does not exist"));
        
        if (post.getUser( ) != getCurrentUser( )) {
            throw new ResourceNotFoundException("post does not exist");
        }

        postrepo.delete(post);
    }
    
}
