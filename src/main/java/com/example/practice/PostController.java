package com.example.practice;

//import org.springframework.beans.factory.annotation.Autowired;
//생성자 주입 방식을 사용함.

import com.example.practice.dto.PostUpdateRequest;
import com.example.practice.dto.PostUpdate;
//import com.example.practice.dto.PostUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//import java.util.Optional;// 변수로 선언하지 않고 바로 메서드 체이닝으로 처리 됐기
//때문에 코드에 쓰지 않게 됨.

@RestController
@RequestMapping("/posts")//posts와 post의 차이점- 단수와 복수의 개념
public class PostController {

    //@Autowired  테스트하기 어렵고, 불변성 유지가 어렵고, 순환 참조 발생 시 원인 파악이 어려움.
    private PostService postService;

    // 생성자 주입 방식 사용
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 전체 게시글 조회 (GET /posts)
    /*
    @GetMapping
    public ResponseEntity<List<PostEntity>> getAllPosts() {
        List<PostEntity> posts = postService.getAllPosts(); -> post라는 변수를 만들 필요가 없음
        return ResponseEntity.ok(posts);
    }
*/
    @GetMapping
    public ResponseEntity<List<PostEntity>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // 단일 게시글 조회 (GET /posts/{id})
    /*
    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> getPostById(@PathVariable Long id) {
        Optional<PostEntity> post = postService.getPostById(id);// 변수에 저장하지 않고 바로 체이닝 해서 리팩토링
        return post.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
*/
    @GetMapping("/{id}")
    public ResponseEntity<PostEntity> getPostById(@PathVariable Long id) {
        try {
            PostEntity post = postService.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /*
    // 새 게시글 생성 (POST /posts)
    @PostMapping
    public ResponseEntity<PostEntity> createPost(@RequestBody PostEntity post) {
        PostEntity savedPost = postService.createPost(post);
        return ResponseEntity.ok(savedPost);// 요청받은 게시글을 저장해서 바로 응답을 보내야 해서 간결하게 바꿈
    }
    */
    @PostMapping
    public ResponseEntity<PostEntity> createPost(@RequestBody PostEntity post) {
        return ResponseEntity.ok(postService.createPost(post));
    }


    /*
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
*/
//    @PutMapping("/{id}")
//    public ResponseEntity<PostEntity> updatePost(@PathVariable Long id, @RequestBody PostEntity post) {
//        PostEntity updatedPost = postService.updatePost(id, post);
//        return (updatedPost != null) ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
//    } //if/else 제외하고 ?:로 짧고 명확하게 표현
    @PutMapping("/v1/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request
    ) {
        postService.PostUpdateRequest(id, request);
        return ResponseEntity.ok().build();
    }
    // TODO : DTO를 사용한 PutMapping update API 생성하기!!

    @PutMapping("/v2/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdate request
    ) {
        postService.updatePost(id, request);
        return ResponseEntity.ok().build();
    }


    // 게시글 삭제 (DELETE /posts/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
// commit 연습
// commit 연습 2
//Spring Boot에서 @RestController를 사용하면 기본적으로 HttpMessageConverter를 통해 Java 객체가 JSON으로 자동 직렬화됩니다