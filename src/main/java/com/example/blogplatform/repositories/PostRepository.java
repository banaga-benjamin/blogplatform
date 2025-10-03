package com.example.blogplatform.repositories;

import java.util.Optional;
import com.example.blogplatform.models.Post;
import com.example.blogplatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndUser(Long id, User user);
}
