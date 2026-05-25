package com.example.demo.repository;

import com.example.demo.model.Article;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository {

    private final List<Article> articles = new ArrayList<>();
    private Long nextId = 1L;

    public ArticleRepository() {
        articles.add(new Article(nextId++, 1L, 1L, "제목0", ""));
        articles.add(new Article(nextId++, 2L, 1L, "제목1", "내용입니다!!"));
        articles.add(new Article(nextId++, 3L, 1L, "제목2", "내용입니다!!내용입니다!!"));
        articles.add(new Article(nextId++, 4L, 1L, "제목3", "내용입니다!!내용입니다!!내용입니다!!"));
    }

    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    public Optional<Article> findById(Long id) {
        return articles.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    public Article save(Article article) {
        Article newArticle = new Article(nextId++, article.getMemberId(), article.getBoardId(),
                article.getTitle(), article.getContent());
        articles.add(newArticle);
        return newArticle;
    }

    public Optional<Article> update(Long id, String title, String content) {
        Optional<Article> found = findById(id);
        found.ifPresent(a -> {
            if (title != null && !title.isBlank()) a.setTitle(title);
            if (content != null) a.setContent(content);
            a.setUpdatedAt(LocalDateTime.now());
        });
        return found;
    }

    public boolean delete(Long id) {
        return articles.removeIf(a -> a.getId().equals(id));
    }
}