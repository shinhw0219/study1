package com.example.practice;


import com.example.practice.dto.request.PostUpdateRequest;
import com.example.practice.dto.request.PostUpdate;
//import com.example.practice.dto.request.PostUpdateRequest;
import com.example.practice.dto.response.PostResponseDTO;
import com.example.practice.dto.response.PostResponseTitleDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {


    private final PostService postService;

//	전체 정보 기반 게시글 생성
    @PostMapping("/all/create")
    public ResponseEntity<?> createPost(@RequestBody PostResponseDTO request) {
        try {
            PostResponseDTO response = postService.createPost(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

//전체 게시글 조회
    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        try {
            List<PostResponseTitleDto> posts = postService.getAllPosts();
            return ResponseEntity.ok(posts);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
//	특정 게시글 조회 (ID 기반)
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getOnePost(@PathVariable("id") Long id) {
        try {
            PostResponseTitleDto dto = postService.getPostById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // 제목 기반 게시글 생성
    @PostMapping("/title/create")
    public ResponseEntity<?> createPost(@RequestBody PostUpdateRequest request) {
        try {
            // 예시로 저장하는 로직 가정 (PostEntity를 DTO로 감싸지 않고 저장만)
            PostResponseTitleDto response = postService.createPost(request); // DTO를 넘김
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 게시글 수정 전체
@PutMapping("/all/{id}")
public ResponseEntity<?> updatePostV1(
        @PathVariable Long id,
        @RequestBody PostUpdateRequest request
) {
    try {
        PostResponseTitleDto response = postService.updatePostWithResponse(id, request);
        return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(null);
    }
}
    // TODO : DTO를 사용한 PutMapping update API 생성하기!!
    // 게시글 수정 타이틀이랑 내용만
    @PutMapping("/v2/{id}")
    public ResponseEntity<?> updatePostV2(
            @PathVariable Long id,
            @RequestBody PostUpdate request
    ) {
        try {
            PostResponseTitleDto response = postService.updatePost(id, request);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
//게시글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
// commit 연습
// commit 연습 2
//Spring Boot에서 @RestController를 사용하면 기본적으로 HttpMessageConverter를 통해 Java 객체가 JSON으로 자동 직렬화됩니다



// TODO : yml 안올라가도록 git push 하기

