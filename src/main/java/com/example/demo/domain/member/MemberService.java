package com.example.demo.domain.member;

import com.example.demo.domain.article.ArticleRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public MemberService(MemberRepository memberRepository, ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional(readOnly = true)
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member getById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다. id=" + id));
    }

    @Transactional
    public Member create(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public Member update(Long id, Member memberData) {
        Member existing = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다. id=" + id));

        memberRepository.findByEmail(memberData.getEmail())
                .filter(m -> !m.getId().equals(id))
                .ifPresent(m -> {
                    throw new ConflictException("이미 사용 중인 이메일입니다. email=" + memberData.getEmail());
                });

        existing.setName(memberData.getName());
        existing.setEmail(memberData.getEmail());
        existing.setPassword(memberData.getPassword());
        return memberRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다. id=" + id));

        if (articleRepository.existsByMember_Id(id)) {
            throw new BadRequestException("게시물이 존재하는 사용자는 삭제할 수 없습니다.");
        }

        memberRepository.deleteById(id);
    }
}
