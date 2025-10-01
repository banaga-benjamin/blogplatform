package com.example.blogplatform.dtos;

import jakarta.validation.constraints.Size;

public class CommentRequest {

    @Size(max = 5000, message = "content must be less than 5000 characters long")
    private String content;

    public String getContent( ) { return content; }
    public void setContent(String content) {this.content = content; }

}
