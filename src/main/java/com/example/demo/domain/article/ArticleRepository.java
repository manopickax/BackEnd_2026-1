package com.example.demo.domain.article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByBoard_Id(Long boardId);
    boolean existsByMember_Id(Long memberId);
}
