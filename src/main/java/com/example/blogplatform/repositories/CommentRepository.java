package com.example.blogplatform.repositories;

import java.util.List;
import java.util.Optional;
import com.example.blogplatform.models.Post;
import com.example.blogplatform.models.User;
import com.example.blogplatform.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    Optional<Comment> findByIdAndPost(Long id, Post post);
    Optional<Comment> findByIdAndPostAndUser(Long id, Post post, User user);
}
