package com.example.practice;


import java.util.Optional;

import com.example.practice.dto.request.PostUpdate;
import com.example.practice.dto.request.PostUpdateRequest;
//import com.example.practice.dto.response.PostResponseDTO;
import com.example.practice.dto.response.PostResponseTitleDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;// 주석처리 됐었는데 이유는 lombok이 정의가 되어있지 않아서였음.
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //게시글 조회하는 코드
    public List<PostResponseTitleDto> getAllPosts() {
        List<PostEntity> postList = postRepository.findAll();

        if (postList.isEmpty()) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
        }

        return postList.stream()
                .map(post -> PostResponseTitleDto.builder()
                        .title(post.getTitle())
                        .build())
                .toList();
    }

    // 게시글에서 id만 조회하는 코드
    @Transactional
    public void getPostBynum(Long id) {
        postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다. ID: " + id));

    }


    //전체 게시글을 생성하는 코드
    @Transactional
    public void createPost(PostUpdateRequest request) {
        PostEntity post = PostEntity.builder()

                .title(request.getTitle())
                .content(request.getContent())
                .username(request.getName())   // name → username 매핑
                .email(request.getEmail())
                .build();

        postRepository.save(post);


    }

    //제목과 내용만 생성하는 코드
    @Transactional
    public void createPost(PostUpdate request) { // => 수정!! 반화타입, 매개변수...

        PostEntity post = PostEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();


        postRepository.save(post);


    }

    @Transactional
    public void updatePost(Long id, PostUpdate request) {
        Optional<PostEntity> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("찾을 수 없음: " + id);
        }

        PostEntity post = optionalPost.get();
        post.updateFromDto(request);
    }


    //게시글 전체 수정 코드
    @Transactional
    public void PostUpdateRequest(Long id, PostUpdateRequest request) {
        Optional<PostEntity> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("수정할 게시글이 존재하지 않습니다. ID: " + id);
        }

        PostEntity post = optionalPost.get();
        post.updateFromDto(request); // 실제 수정 로직

    }

    // 게시글 삭제하는 코드
    @Transactional
    public void deletePost(Long id) {
        Optional<PostEntity> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다. ID: " + id);
        }

        postRepository.delete(optionalPost.get());
    }
}


