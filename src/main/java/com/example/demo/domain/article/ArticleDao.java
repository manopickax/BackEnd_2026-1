package com.example.demo.domain.article;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Article> findAll() {
        return entityManager.createQuery("SELECT a FROM Article a", Article.class)
                .getResultList();
    }

    public List<Article> findByBoardId(Long boardId) {
        return entityManager
                .createQuery("SELECT a FROM Article a WHERE a.boardId = :boardId", Article.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Article.class, id));
    }

    public boolean existsByMemberId(Long memberId) {
        Long count = entityManager
                .createQuery("SELECT COUNT(a) FROM Article a WHERE a.memberId = :memberId", Long.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
        return count > 0;
    }

    public boolean existsByBoardId(Long boardId) {
        Long count = entityManager
                .createQuery("SELECT COUNT(a) FROM Article a WHERE a.boardId = :boardId", Long.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
        return count > 0;
    }

    public Article save(Article article) {
        entityManager.persist(article);
        return article;
    }

    // 영속 상태 엔티티 필드 변경 → 트랜잭션 커밋 시 dirty checking으로 UPDATE 자동 반영
    // created_date / modified_date 는 insertable=false, updatable=false 로 MySQL이 관리
    public Article update(Article article) {
        Article managed = entityManager.find(Article.class, article.getId());
        managed.setTitle(article.getTitle());
        managed.setContent(article.getContent());
        managed.setMemberId(article.getMemberId());
        managed.setBoardId(article.getBoardId());
        return managed;
    }

    public void deleteById(Long id) {
        Article article = entityManager.find(Article.class, id);
        if (article != null) {
            entityManager.remove(article);
        }
    }
}
