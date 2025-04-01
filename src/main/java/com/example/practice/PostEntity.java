package com.example.practice;

import com.example.practice.dto.PostUpdate;
import com.example.practice.dto.PostUpdateRequest;
import jakarta.persistence.*;
//이건 JPA (Java Persistence API) 관련 어노테이션들을 사용하기 위해 필요함
//데이터베이스와 객체를 연결해주는 도구 (like 버스,jpa)
import java.time.LocalDateTime;// 현재 시간을 불러오기 위해 필요함.

@Entity
public class PostEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public PostEntity(String name, String email, String title, String content) {
        this.name = name;
        this.email = email;
        this.title = title;
        this.content = content;
    }

    protected PostEntity() {// protected로 바꾼 이유는? 외부에서 실수로 쓰지 않게 하려고 (캡슐화)

    }

    // createdAt은 엔티티가 저장되기 전에 자동 세팅됨

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }

    public void updateFromDto(PostUpdate dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
   public void updateFromDto(PostUpdateRequest dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
}

