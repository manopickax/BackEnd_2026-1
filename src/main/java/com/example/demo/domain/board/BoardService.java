package com.example.demo.domain.board;

import com.example.demo.domain.article.ArticleDao;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardDao boardDao;
    private final ArticleDao articleDao;

    public BoardService(BoardDao boardDao, ArticleDao articleDao) {
        this.boardDao = boardDao;
        this.articleDao = articleDao;
    }

    @Transactional(readOnly = true)
    public List<Board> getAll() {
        return boardDao.findAll();
    }

    @Transactional(readOnly = true)
    public Board getById(Long id) {
        return boardDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + id));
    }

    @Transactional
    public Board create(Board board) {
        return boardDao.save(board);
    }

    @Transactional
    public Board update(Long id, Board boardData) {
        Board existing = boardDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + id));

        existing.setName(boardData.getName());
        return boardDao.update(existing);
    }

    @Transactional
    public void delete(Long id) {
        boardDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + id));

        if (articleDao.existsByBoardId(id)) {
            throw new BadRequestException("게시물이 존재하는 게시판은 삭제할 수 없습니다.");
        }

        boardDao.deleteById(id);
    }
}
