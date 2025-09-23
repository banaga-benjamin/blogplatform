package com.example.blogplatform.models;

import jakarta.persistence.*;

@Entity
@Table(name = "posttable")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable  = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getId( ) { return id; }

    public String getTitle( ) { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent( ) { return content; }
    public void setContent(String content) { this.content = content; }

    public User getUser( ) { return user; }
    public void setUser(User user) { this.user = user; }
    
}
