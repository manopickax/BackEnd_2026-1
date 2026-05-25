package com.example.demo.dto;

public class ArticleRequestDto {

    private Long memberId;
    private Long boardId;
    private String title;
    private String content;

    public ArticleRequestDto() {}

    public Long getMemberId() { return memberId; }
    public Long getBoardId() { return boardId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }

    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public void setBoardId(Long boardId) { this.boardId = boardId; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
}