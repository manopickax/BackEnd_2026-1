package com.example.demo.domain.article;

import com.example.demo.domain.board.BoardRepository;
import com.example.demo.domain.member.MemberRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository,
                          MemberRepository memberRepository,
                          BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public List<Article> getByBoardId(Long boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + boardId));
        return articleRepository.findByBoardId(boardId);
    }

    public Article getById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다. id=" + id));
    }

    public Article create(Article article) {
        validateReferences(article.getMemberId(), article.getBoardId());
        article.setId(null);
        return articleRepository.save(article);
    }

    public Article update(Long id, Article articleData) {
        articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다. id=" + id));

        validateReferences(articleData.getMemberId(), articleData.getBoardId());

        articleData.setId(id);
        return articleRepository.save(articleData);
    }

    public void delete(Long id) {
        articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다. id=" + id));
        articleRepository.deleteById(id);
    }

    private void validateReferences(Long memberId, Long boardId) {
        if (!memberRepository.existsById(memberId)) {
            throw new BadRequestException("존재하지 않는 사용자입니다. memberId=" + memberId);
        }
        if (!boardRepository.existsById(boardId)) {
            throw new BadRequestException("존재하지 않는 게시판입니다. boardId=" + boardId);
        }
    }
}
