package com.example.blogplatform.models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "commenttable")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Long getId( ) { return id; }

    public String getContent( ) { return content; }
    public void setContent(String content) { this.content = content; }

    public User getUser( ) { return user; }
    public void setUser(User user) { this.user = user; }

    public Post getPost( ) { return post; }
    public void setPost(Post post) { this.post = post; }

}
