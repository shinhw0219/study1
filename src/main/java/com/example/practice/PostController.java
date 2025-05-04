package com.example.practice;


import com.example.practice.dto.request.PostUpdateRequest;
import com.example.practice.dto.request.PostUpdate;
//import com.example.practice.dto.request.PostUpdateRequest;
//import com.example.practice.dto.response.PostResponseDTO;
import com.example.practice.dto.response.PostResponseTitleDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 문단정렬 : ctrl + alt + L


// CRUD 


@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {


    private final PostService postService;


    //전체 게시글 조회
    @GetMapping("/all")
    public ResponseEntity<String> getAllPosts() {
        try {
            List<PostResponseTitleDto> posts = postService.getAllPosts();
            return ResponseEntity.ok(posts.toString()); // 객체 리스트를 문자열로 강제 변환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    //	특정 게시글 조회 (ID 기반)
    @GetMapping("/find/{id}")
    public ResponseEntity<String> getOnePost(@PathVariable("id") Long id) {
        try {
            postService.getPostBynum(id);
            return ResponseEntity.ok("조회 가능!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    //	전체 정보 기반 게시글 생성
    @PostMapping("/all/create")
    public ResponseEntity<String> createPost(@RequestBody PostUpdateRequest request) {
        try {
            postService.createPost(request); // 반환값 무시
            return ResponseEntity.ok("게시글 생성 완료!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // 제목 기반 게시글 생성
    @PostMapping("/title/create")
    public ResponseEntity<String> createPost(@RequestBody PostUpdate request) {
        try {
            // 예시로 저장하는 로직 가정 (PostEntity를 DTO로 감싸지 않고 저장만)
//            PostResponseTitleDto response = postService.createPost(request); // DTO를 넘김
            postService.createPost(request); // DTO를 넘김
            return ResponseEntity.ok().body("만들었슈~~");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("제목 기반 게시글 생성 실패:" + e.getMessage());
        }
    }

    // 게시글 수정 전체
    @PutMapping("/all/{id}")
    public ResponseEntity<String> updatePostV1(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request
    ) {
        try {
            postService.PostUpdateRequest(id, request); // 응답 DTO는 사용하지 않음
            return ResponseEntity.ok("게시글 수정 완료!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("수정 실패: " + e.getMessage());
        }
    }

    // 게시글 수정 타이틀이랑 내용만
    @PutMapping("/v2/{id}")
    public ResponseEntity<String> updatePostV2(
            @PathVariable Long id,
            @RequestBody PostUpdate request
    ) {
        try {
            postService.updatePost(id, request); // 반환값 사용 안 함
            return ResponseEntity.ok("게시글 제목과 내용이 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("수정 실패: " + e.getMessage());
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

