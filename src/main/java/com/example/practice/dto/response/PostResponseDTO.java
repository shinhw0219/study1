package com.example.practice.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private String name;
    private String email;
    private String title;
    private String content;

}
