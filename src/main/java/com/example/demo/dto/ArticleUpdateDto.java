package com.example.demo.dto;

public class ArticleUpdateDto {

    private String title;
    private String content;

    public ArticleUpdateDto() {}

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
}