package com.example.demo.domain.member;

import com.example.demo.domain.article.ArticleRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.UnauthorizedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new ConflictException("이미 사용 중인 이메일입니다. email=" + member.getEmail());
                });

        member.setPassword(passwordEncoder.encode(member.getPassword()));
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
        existing.setPassword(passwordEncoder.encode(memberData.getPassword()));
        return memberRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        return member;
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
