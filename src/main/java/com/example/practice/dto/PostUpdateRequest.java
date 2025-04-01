package com.example.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequest {
    private String name;
    private String email;
    private String title;
    private String content;
}
