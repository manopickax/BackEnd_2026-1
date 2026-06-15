package com.example.demo.domain.article;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ArticleRepository {

    private final Map<Long, Article> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Article> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<Article> findByBoardId(Long boardId) {
        return store.values().stream()
                .filter(a -> a.getBoardId().equals(boardId))
                .collect(Collectors.toList());
    }

    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public boolean existsByMemberId(Long memberId) {
        return store.values().stream().anyMatch(a -> a.getMemberId().equals(memberId));
    }

    public boolean existsByBoardId(Long boardId) {
        return store.values().stream().anyMatch(a -> a.getBoardId().equals(boardId));
    }

    public Article save(Article article) {
        if (article.getId() == null) {
            article.setId(idGenerator.getAndIncrement());
        }
        store.put(article.getId(), article);
        return article;
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}
