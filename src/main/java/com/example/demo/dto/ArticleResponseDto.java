package com.example.demo.dto;

import java.time.LocalDateTime;

public class ArticleResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime date;
    private String content;

    public ArticleResponseDto(Long id, String title, String author, LocalDateTime date, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.date = date;
        this.content = content;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public LocalDateTime getDate() { return date; }
    public String getContent() { return content; }
}