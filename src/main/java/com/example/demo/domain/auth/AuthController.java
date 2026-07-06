package com.example.demo.domain.auth;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.member.MemberService;
import com.example.demo.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입은 기존 POST /members(MemberController)를 그대로 사용한다.

    @PostMapping("/login")
    public Member login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        Member member = memberService.login(request.email(), request.password());

        // 세션 생성 → 응답 헤더에 Set-Cookie: JSESSIONID=... 가 내려간다.
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER_ID, member.getId());

        return member;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @GetMapping("/me")
    public Member me(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER_ID) == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        Long loginMemberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER_ID);
        return memberService.getById(loginMemberId);
    }
}
