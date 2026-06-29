package com.example.demo.domain.member;

import com.example.demo.domain.article.ArticleDao;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {

    private final MemberDao memberDao;
    private final ArticleDao articleDao;

    public MemberService(MemberDao memberDao, ArticleDao articleDao) {
        this.memberDao = memberDao;
        this.articleDao = articleDao;
    }

    @Transactional(readOnly = true)
    public List<Member> getAll() {
        return memberDao.findAll();
    }

    @Transactional(readOnly = true)
    public Member getById(Long id) {
        return memberDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다. id=" + id));
    }

    @Transactional
    public Member create(Member member) {
        return memberDao.save(member);
    }

    @Transactional
    public Member update(Long id, Member memberData) {
        Member existing = memberDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다. id=" + id));

        memberDao.findByEmail(memberData.getEmail())
                .filter(m -> !m.getId().equals(id))
                .ifPresent(m -> {
                    throw new ConflictException("이미 사용 중인 이메일입니다. email=" + memberData.getEmail());
                });

        existing.setName(memberData.getName());
        existing.setEmail(memberData.getEmail());
        existing.setPassword(memberData.getPassword());
        return memberDao.update(existing);
    }

    @Transactional
    public void delete(Long id) {
        memberDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다. id=" + id));

        if (articleDao.existsByMemberId(id)) {
            throw new BadRequestException("게시물이 존재하는 사용자는 삭제할 수 없습니다.");
        }

        memberDao.deleteById(id);
    }
}
