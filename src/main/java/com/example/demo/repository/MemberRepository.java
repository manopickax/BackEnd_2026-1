package com.example.demo.repository;

import com.example.demo.model.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final List<Member> members = new ArrayList<>();

    public MemberRepository() {
        members.add(new Member(1L, "회원0", "member0@email.com", "password0"));
        members.add(new Member(2L, "회원1", "member1@email.com", "password1"));
        members.add(new Member(3L, "회원2", "member2@email.com", "password2"));
        members.add(new Member(4L, "회원3", "member3@email.com", "password3"));
    }

    public List<Member> findAll() { return new ArrayList<>(members); }

    public Optional<Member> findById(Long id) {
        return members.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }
}