package com.example.demo.domain.board;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BoardRepository {

    private final Map<Long, Board> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Board> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    public Board save(Board board) {
        if (board.getId() == null) {
            board.setId(idGenerator.getAndIncrement());
        }
        store.put(board.getId(), board);
        return board;
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}
