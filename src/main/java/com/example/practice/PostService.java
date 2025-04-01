package com.example.practice;

import com.example.practice.PostEntity;
//import org.springframework.beans.factory.annotation.Autowired;

import com.example.practice.dto.PostUpdate;
import com.example.practice.dto.PostUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;// 주석처리 됐었는데 이유는 lombok이 정의가 되어있지 않아서였음.
import org.springframework.stereotype.Service;
import java.util.List;
//import com.example.practice.dto.PostUpdateRequest;
//import java.util.Optional;// 변수를 거치지 않고 바로 반환

@Service
@RequiredArgsConstructor
public class PostService {

    // PostRepository를 자동 주입받습니다.
    private final PostRepository postRepository;// lombok를 쓰려면 final를 써야 함 Why? 불변성 유지, 의존성 누락 방지, 테스티 시에도 명확
    //만약 final를 빼면  Lombok입장에서 생성자가 만들어야 할 필드가 없네? 하고 무시함
    //결국 아무 역할도 못하게 됨.

    // 전체 게시글 목록 조회
    public List<PostEntity> getAllPosts() {
        return postRepository.findAll();
    }

    // 특정 게시글 ID로 조회
    public PostEntity getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + id));
    }// postService 가 예외를 던지게 했다면 컨드롤러도 수정해야 함.

    // 새 게시글 생성 및 저장
    public PostEntity createPost(PostEntity post) {
        return postRepository.save(post);
    }
    @Transactional
    public void updatePost(Long id, PostUpdate request) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        post.updateFromDto(request);
    }
    @Transactional
    public void PostUpdateRequest(Long id, PostUpdateRequest request){
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id:"+ id));

        post.updateFromDto(request);
    }
/*
    // 게시글 수정: 주어진 ID를 가진 게시글을 찾아 업데이트 후 저장합니다.
    public PostEntity updatePost(Long id, PostEntity updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setName(updatedPost.getName());
                    post.setEmail(updatedPost.getEmail());
                    // 필요 시 다른 필드도 업데이트하세요.
                    return postRepository.save(post);
                }).orElse(null);// 흐름에서 NullPointerException 위험이 생겨 클라이언트 입장에서도 왜 실패했는지 알기 어려움
    }
*/
//public PostEntity updatePost(Long id, PostEntity updatedPost) {
//    PostEntity post = postRepository.findById(id)
//            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + id));
//
//    post.setName(updatedPost.getName());
//    post.setEmail(updatedPost.getEmail());
//
//    return postRepository.save(post);
//}
//    // 게시글 삭제: 주어진 ID의 게시글을 삭제합니다.
//    /*
//    public void deletePost(Long id) {
//        postRepository.deleteById(id); 정말 삭제가 됐는지 안 됐는지 알 수 없음.
//    }
//    */
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {//그래서 if문을 쓰는 것임
            throw new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다. ID: " + id);
        }
        postRepository.deleteById(id);
    }
}