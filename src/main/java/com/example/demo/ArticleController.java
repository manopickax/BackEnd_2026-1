package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private Map<Long, Article> articleMap = new HashMap<>();
    private Long sequence = 0L;

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        sequence++;
        article.setId(sequence);
        articleMap.put(sequence, article);

        return ResponseEntity.ok(article);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        if (!articleMap.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articleMap.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article updateParam) {
        if (!articleMap.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        Article article = articleMap.get(id);
        article.setTitle(updateParam.getTitle());
        article.setContent(updateParam.getContent());

        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        if (!articleMap.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        articleMap.remove(id); // Map에서 삭제
        return ResponseEntity.ok().build();
    }
}
