package com.example.demo.service;

import com.example.demo.model.Article;
import com.example.demo.model.Board;
import com.example.demo.model.Member;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsViewService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public PostsViewService(ArticleRepository articleRepository,
                            MemberRepository memberRepository,
                            BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public String getBoardName() {
        return boardRepository.findAll().stream()
                .findFirst().map(Board::getName).orElse("게시판");
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public String getMemberName(Long memberId) {
        return memberRepository.findById(memberId)
                .map(Member::getName).orElse("알 수 없음");
    }
}