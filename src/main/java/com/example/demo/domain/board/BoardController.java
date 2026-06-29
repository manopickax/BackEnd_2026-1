package com.example.demo.domain.board;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public List<Board> getAll() {
        return boardService.getAll();
    }

    @GetMapping("/{id}")
    public Board getById(@PathVariable Long id) {
        return boardService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Board create(@Valid @RequestBody Board board) {
        return boardService.create(board);
    }

    @PutMapping("/{id}")
    public Board update(@PathVariable Long id, @Valid @RequestBody Board board) {
        return boardService.update(id, board);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        boardService.delete(id);
    }
}
