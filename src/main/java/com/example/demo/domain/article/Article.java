package com.example.demo.domain.article;

import com.example.demo.domain.board.Board;
import com.example.demo.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member member;

    @NotNull(message = "게시판 ID는 필수입니다.")
    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonIgnoreProperties({"articles"})
    private Board board;

    @Column(name = "created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date", insertable = false, updatable = false)
    private LocalDateTime modifiedDate;

    public Article() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public LocalDateTime getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(LocalDateTime modifiedDate) { this.modifiedDate = modifiedDate; }
}
