package com.example.demo.repository;

import com.example.demo.model.Board;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {

    private final List<Board> boards = new ArrayList<>();

    public BoardRepository() {
        boards.add(new Board(1L, "자유게시판"));
    }

    public List<Board> findAll() { return new ArrayList<>(boards); }

    public Optional<Board> findById(Long id) {
        return boards.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }
}