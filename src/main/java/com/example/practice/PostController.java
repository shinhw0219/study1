package com.example.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    // 전체 게시글 조회 (GET /posts)
    @GetMapping
    public ResponseEntity<List<PostEntity>> getAllPosts() {
        List<PostEntity> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 단일 게시글 조회 (GET /posts/{id})
    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> getPostById(@PathVariable Long id) {
        Optional<PostEntity> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 새 게시글 생성 (POST /posts)
    @PostMapping
    public ResponseEntity<PostEntity> createPost(@RequestBody PostEntity post) {
        PostEntity savedPost = postService.createPost(post);
        return ResponseEntity.ok(savedPost);
    }

    // 게시글 수정 (PUT /posts/{id})
    @PutMapping("/{id}")
    public ResponseEntity<PostEntity> updatePost(@PathVariable Long id, @RequestBody PostEntity post) {
        PostEntity updatedPost = postService.updatePost(id, post);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시글 삭제 (DELETE /posts/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
