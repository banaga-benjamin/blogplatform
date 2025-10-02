package com.example.blogplatform.repositories;

import com.example.blogplatform.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // default query methods for now
}
