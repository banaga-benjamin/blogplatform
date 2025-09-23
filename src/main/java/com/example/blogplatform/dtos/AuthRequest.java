package com.example.blogplatform.dtos;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public class AuthRequest {

    @NotNull
    @Size(min = 5, max = 20, message = "username must be between one and twenty characters long")
    private String username;

    @NotNull
    @Size(min = 5, max = 30, message = "password must be between five and thirty characters long")
    private String password;

    public String getUsername( ) { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword( ) { return password; }
    public void setPassword(String password) { this.password = password; }

}
