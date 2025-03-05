package com.example.practice;

import com.example.practice.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    // PostRepository를 자동 주입받습니다.
    @Autowired
    private PostRepository postRepository;

    // 전체 게시글 목록 조회
    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    // 특정 게시글 ID로 조회
    public Optional<PostEntity> getPostById(Long id) {
        return postRepository.findById(id);
    }

    // 새 게시글 생성 및 저장
    public PostEntity createPost(PostEntity post) {
        return postRepository.save(post);
    }

    // 게시글 수정: 주어진 ID를 가진 게시글을 찾아 업데이트 후 저장합니다.
    public PostEntity updatePost(Long id, PostEntity updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setName(updatedPost.getName());
                    post.setEmail(updatedPost.getEmail());
                    // 필요 시 다른 필드도 업데이트하세요.
                    return postRepository.save(post);
                }).orElse(null);
    }

    // 게시글 삭제: 주어진 ID의 게시글을 삭제합니다.
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}