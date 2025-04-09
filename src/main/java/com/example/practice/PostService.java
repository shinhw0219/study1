package com.example.practice;


import java.util.Optional;
import com.example.practice.dto.request.PostUpdate;
import com.example.practice.dto.request.PostUpdateRequest;
import com.example.practice.dto.response.PostResponseDTO;
import com.example.practice.dto.response.PostResponseTitleDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;// 주석처리 됐었는데 이유는 lombok이 정의가 되어있지 않아서였음.
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


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

    @Transactional
    public PostResponseTitleDto getPostById(Long id) {
        Optional<PostEntity> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다. ID: " + id);
        }

        PostEntity post = optionalPost.get();

        return PostResponseTitleDto.builder()
                .title(post.getTitle())
                .build();
    }

    @Transactional
    public PostResponseTitleDto createPost(PostUpdateRequest request) {
        PostEntity post = PostEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .name(request.getName())   // name → username 매핑
                .email(request.getEmail())
                .build();

        PostEntity savedPost = postRepository.save(post);

        return PostResponseTitleDto.builder()
                .title(savedPost.getTitle())
                .build();
    }
    //응답 DTO
    @Transactional
    public PostResponseDTO createPost(PostResponseDTO request) {

        PostEntity post = PostEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();


        PostEntity saved = postRepository.save(post);

        return PostResponseDTO.builder()

                .title(saved.getTitle())
                .content(saved.getContent())
                .build();
    }

    @Transactional
    public PostResponseTitleDto updatePost(Long id, PostUpdate request) {
        Optional<PostEntity> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("찾을 수 있음: " + id);
        }

        PostEntity post = optionalPost.get();
        post.updateFromDto(request);

        return PostResponseTitleDto.builder()
                .title(post.getTitle())
                .build();
    }



    @Transactional
    public PostResponseTitleDto updatePostWithResponse(Long id, PostUpdateRequest request) {
        Optional<PostEntity> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("수정할 게시글이 존재하지 않습니다. ID: " + id);
        }

        PostEntity post = optionalPost.get();
        post.updateFromDto(request);

        return PostResponseTitleDto.builder()
                .title(post.getTitle())
                .build();
    }

    @Transactional
    public void deletePost(Long id) {
        Optional<PostEntity> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            throw new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다. ID: " + id);
        }

        postRepository.delete(optionalPost.get());
    }
}


