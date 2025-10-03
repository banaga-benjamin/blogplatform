package com.example.blogplatform.repositories;

import java.util.List;
import com.example.blogplatform.models.Post;
import com.example.blogplatform.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
