package com.example.demo.service;

import com.example.demo.dto.ArticleRequestDto;
import com.example.demo.dto.ArticleResponseDto;
import com.example.demo.dto.ArticleUpdateDto;
import com.example.demo.model.Article;
import com.example.demo.model.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
    }

    public List<ArticleResponseDto> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<ArticleResponseDto> getArticleById(Long id) {
        return articleRepository.findById(id).map(this::toResponseDto);
    }

    public ArticleResponseDto createArticle(ArticleRequestDto requestDto) {
        Article article = new Article(null, requestDto.getMemberId(), requestDto.getBoardId(),
                requestDto.getTitle(), requestDto.getContent());
        return toResponseDto(articleRepository.save(article));
    }

    public Optional<ArticleResponseDto> updateArticle(Long id, ArticleUpdateDto updateDto) {
        return articleRepository.update(id, updateDto.getTitle(), updateDto.getContent())
                .map(this::toResponseDto);
    }

    public boolean deleteArticle(Long id) {
        return articleRepository.delete(id);
    }

    private ArticleResponseDto toResponseDto(Article article) {
        String authorName = memberRepository.findById(article.getMemberId())
                .map(Member::getName)
                .orElse("알 수 없음");
        return new ArticleResponseDto(article.getId(), article.getTitle(), authorName,
                article.getCreatedAt(), article.getContent());
    }
}