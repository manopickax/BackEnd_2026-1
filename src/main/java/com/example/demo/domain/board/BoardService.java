package com.example.demo.domain.board;

import com.example.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional(readOnly = true)
    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Board getById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + id));
    }

    @Transactional
    public Board create(Board board) {
        return boardRepository.save(board);
    }

    @Transactional
    public Board update(Long id, Board boardData) {
        Board existing = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + id));

        existing.setName(boardData.getName());
        return boardRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시판입니다. id=" + id));

        boardRepository.delete(board);
    }
}
