package com.example.demo.Repository.Post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostDto {
    private String category;
    private String title;
    private String content;
}
