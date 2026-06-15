package com.example.demo.domain.board;

import com.example.demo.domain.article.ArticleRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    public BoardService(BoardRepository boardRepository, ArticleRepository articleRepository) {
        this.boardRepository = boardRepository;
        this.articleRepository = articleRepository;
    }

    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    public Board getById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + id));
    }

    public Board create(Board board) {
        board.setId(null);
        return boardRepository.save(board);
    }

    public Board update(Long id, Board boardData) {
        Board existing = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + id));

        existing.setName(boardData.getName());
        existing.setDescription(boardData.getDescription());
        return boardRepository.save(existing);
    }

    public void delete(Long id) {
        boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + id));

        if (articleRepository.existsByBoardId(id)) {
            throw new BadRequestException("게시물이 존재하는 게시판은 삭제할 수 없습니다.");
        }

        boardRepository.deleteById(id);
    }
}
