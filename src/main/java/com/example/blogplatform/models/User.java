package com.example.blogplatform.models;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "usertable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    public Long getId( ) { return id; }

    public String getUsername( ) { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword( ) { return password; }
    public void setPassword(String password) { this.password = password; }
    
}
