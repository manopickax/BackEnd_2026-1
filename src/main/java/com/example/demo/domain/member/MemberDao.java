package com.example.demo.domain.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Member> findAll() {
        return entityManager.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Member.class, id));
    }

    public Optional<Member> findByEmail(String email) {
        List<Member> result = entityManager
                .createQuery("SELECT m FROM Member m WHERE m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public boolean existsById(Long id) {
        Long count = entityManager
                .createQuery("SELECT COUNT(m) FROM Member m WHERE m.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    // 영속 상태 엔티티 필드 변경 → 트랜잭션 커밋 시 dirty checking으로 UPDATE 자동 반영
    public Member update(Member member) {
        Member managed = entityManager.find(Member.class, member.getId());
        managed.setName(member.getName());
        managed.setEmail(member.getEmail());
        managed.setPassword(member.getPassword());
        return managed;
    }

    public void deleteById(Long id) {
        Member member = entityManager.find(Member.class, id);
        if (member != null) {
            entityManager.remove(member);
        }
    }
}
