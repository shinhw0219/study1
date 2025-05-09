package com.example.practice;

import com.example.practice.dto.request.PostUpdate;
import com.example.practice.dto.request.PostUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    // UserEntity와의 다대일 관계 설정
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // JPA에서 사용하기 위한 protected 기본 생성자 (캡슐화를 위해 외부 접근 제한)
    protected PostEntity() {
    }

    // 엔티티 저장 전 현재 시간으로 createdAt 자동 세팅
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void updateFromDto(PostUpdate dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void updateFromDto(PostUpdateRequest dto) {
        this.username = dto.getName();
        this.email = dto.getEmail();
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
}
