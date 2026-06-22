package com.example.demo.domain.article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 필수입니다.")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Column(name = "content")
    private String content;

    @NotNull(message = "사용자 ID는 필수입니다.")
    @Column(name = "author_id")
    private Long memberId;

    @NotNull(message = "게시판 ID는 필수입니다.")
    @Column(name = "board_id")
    private Long boardId;

    // MySQL DEFAULT CURRENT_TIMESTAMP / ON UPDATE CURRENT_TIMESTAMP 로 관리
    @Column(name = "created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date", insertable = false, updatable = false)
    private LocalDateTime modifiedDate;

    public Article() {}

    public Article(Long id, String title, String content, Long memberId, Long boardId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.boardId = boardId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public Long getBoardId() { return boardId; }
    public void setBoardId(Long boardId) { this.boardId = boardId; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public LocalDateTime getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(LocalDateTime modifiedDate) { this.modifiedDate = modifiedDate; }
}
