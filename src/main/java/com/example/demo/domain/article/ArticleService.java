package com.example.demo.domain.article;

import com.example.demo.domain.board.BoardDao;
import com.example.demo.domain.member.MemberDao;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleDao articleDao;
    private final MemberDao memberDao;
    private final BoardDao boardDao;

    public ArticleService(ArticleDao articleDao, MemberDao memberDao, BoardDao boardDao) {
        this.articleDao = articleDao;
        this.memberDao = memberDao;
        this.boardDao = boardDao;
    }

    @Transactional(readOnly = true)
    public List<Article> getByBoardId(Long boardId) {
        boardDao.findById(boardId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + boardId));
        return articleDao.findByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public Article getById(Long id) {
        return articleDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다. id=" + id));
    }

    @Transactional
    public Article create(Article article) {
        validateReferences(article.getMemberId(), article.getBoardId());
        return articleDao.save(article);
    }

    @Transactional
    public Article update(Long id, Article articleData) {
        articleDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다. id=" + id));

        validateReferences(articleData.getMemberId(), articleData.getBoardId());

        articleData.setId(id);
        return articleDao.update(articleData);
    }

    @Transactional
    public void delete(Long id) {
        articleDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다. id=" + id));
        articleDao.deleteById(id);
    }

    private void validateReferences(Long memberId, Long boardId) {
        if (!memberDao.existsById(memberId)) {
            throw new BadRequestException("존재하지 않는 사용자입니다. memberId=" + memberId);
        }
        if (!boardDao.existsById(boardId)) {
            throw new BadRequestException("존재하지 않는 게시판입니다. boardId=" + boardId);
        }
    }
}
