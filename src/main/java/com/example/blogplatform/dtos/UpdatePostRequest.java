package com.example.blogplatform.dtos;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public class UpdatePostRequest {

    @NotBlank
    @Size(max = 30, message = "title must be within thirty characters long")
    private String title;

    @Size(max = 5000, message = "content must be less than 5000 characters long")
    private String content;

    public String getTitle( ) { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent( ) { return content; }
    public void setContent(String content) {this.content = content; }

}
