package com.example.blogplatform.models;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public Long getId( ) { return id; }

    public String getUsername( ) { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword( ) { return password; }
    public void setPassword(String password) { this.password = password; }
    
}
