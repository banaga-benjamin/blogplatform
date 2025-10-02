package com.example.blogplatform.services;

import org.springframework.stereotype.Service;

import com.example.blogplatform.models.User;
import com.example.blogplatform.models.Comment;
import com.example.blogplatform.dtos.CommentDTO;
import com.example.blogplatform.dtos.CommentRequest;
import com.example.blogplatform.repositories.PostRepository;
import com.example.blogplatform.repositories.UserRepository;
import com.example.blogplatform.repositories.CommentRepository;

import com.example.blogplatform.exceptions.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class CommentServiceImplementation implements CommentService {

    private final UserRepository userrepo;
    private final PostRepository postrepo;
    private final CommentRepository commentrepo;

    public CommentServiceImplementation(CommentRepository commentrepo, UserRepository userrepo, PostRepository postrepo) {
        this.postrepo = postrepo;
        this.userrepo = userrepo;
        this.commentrepo = commentrepo;
    }

    private User getCurrentUser( ) {
        String username = SecurityContextHolder.getContext( ).getAuthentication( ).getName( );
        User user = userrepo.findByUsername(username).orElseThrow(
            ( ) -> new ResourceNotFoundException("user does not exist"));
        return user;
    }

    private CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO commentdto = new CommentDTO( );
        commentdto.setContent(comment.getContent( ));
        commentdto.setId(comment.getId( ));

        return commentdto;
    }
    
    @Override
    public CommentDTO createComment(CommentRequest request, Long pid) {
        Comment comment = new Comment( );
        comment.setUser(getCurrentUser( ));
        comment.setContent(request.getContent( ));
        comment.setPost(postrepo.findById(pid).orElseThrow(( ) -> new ResourceNotFoundException("post does not exist")));

        return mapToCommentDTO(commentrepo.save(comment));
    }


}