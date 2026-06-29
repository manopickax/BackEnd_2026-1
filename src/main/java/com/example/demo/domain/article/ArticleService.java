package com.example.demo.domain.article;

import com.example.demo.domain.board.Board;
import com.example.demo.domain.board.BoardRepository;
import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional(readOnly = true)
    public List<Article> getByBoardId(Long boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + boardId));
        return articleRepository.findByBoard_Id(boardId);
    }

    @Transactional(readOnly = true)
    public Article getById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다. id=" + id));
    }

    @Transactional
    public Article create(Article article) {
        Board board = boardRepository.findById(article.getBoard().getId())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 게시판입니다. boardId=" + article.getBoard().getId()));
        Member member = memberRepository.findById(article.getMember().getId())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 사용자입니다. memberId=" + article.getMember().getId()));

        article.setBoard(board);
        article.setMember(member);
        return articleRepository.save(article);
    }

    @Transactional
    public Article update(Long id, Article articleData) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다. id=" + id));

        Board board = boardRepository.findById(articleData.getBoard().getId())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 게시판입니다. boardId=" + articleData.getBoard().getId()));
        Member member = memberRepository.findById(articleData.getMember().getId())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 사용자입니다. memberId=" + articleData.getMember().getId()));

        article.setTitle(articleData.getTitle());
        article.setContent(articleData.getContent());
        article.setBoard(board);
        article.setMember(member);
        return articleRepository.save(article);
    }

    @Transactional
    public void delete(Long id) {
        articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다. id=" + id));
        articleRepository.deleteById(id);
    }
}
