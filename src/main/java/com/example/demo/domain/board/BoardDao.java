package com.example.demo.domain.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Board> findAll() {
        return entityManager.createQuery("SELECT b FROM Board b", Board.class)
                .getResultList();
    }

    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Board.class, id));
    }

    public boolean existsById(Long id) {
        Long count = entityManager
                .createQuery("SELECT COUNT(b) FROM Board b WHERE b.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

    public Board save(Board board) {
        entityManager.persist(board);
        return board;
    }

    // 영속 상태 엔티티 필드 변경 → 트랜잭션 커밋 시 dirty checking으로 UPDATE 자동 반영
    public Board update(Board board) {
        Board managed = entityManager.find(Board.class, board.getId());
        managed.setName(board.getName());
        return managed;
    }

    public void deleteById(Long id) {
        Board board = entityManager.find(Board.class, id);
        if (board != null) {
            entityManager.remove(board);
        }
    }
}
