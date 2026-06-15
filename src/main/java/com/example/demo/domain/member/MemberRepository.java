package com.example.demo.domain.member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemberRepository {

    private final Map<Long, Member> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Member> findByEmail(String email) {
        return store.values().stream()
                .filter(m -> m.getEmail().equals(email))
                .findFirst();
    }

    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    public Member save(Member member) {
        if (member.getId() == null) {
            member.setId(idGenerator.getAndIncrement());
        }
        store.put(member.getId(), member);
        return member;
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}
