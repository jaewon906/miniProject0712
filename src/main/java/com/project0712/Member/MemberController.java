package com.project0712.Member;

import com.project0712.Auth.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;
    private final SecurityConfig securityConfig;

    @PostMapping("/api/signUp")
    public void signUp(MemberDTO memberDTO) {
        memberServiceImpl.save(memberDTO);
    }

    @GetMapping("/api/signUp")
    public MemberDTO sendMemberInfoToFrontend(MemberDTO memberDTO) {
        return memberDTO;
    }

    @GetMapping("/api/signUp/id")
    public String validateId(MemberDTO memberDTO) {
        return memberServiceImpl.validateDuplicatedId(memberDTO);
    }

    @GetMapping("/api/signUp/nickname")
    public String validateNickname(MemberDTO memberDTO) {
        return memberServiceImpl.validateDuplicatedNickname(memberDTO);
    }

    @GetMapping("/api/signUp/email")
    public String validateEmail(MemberDTO memberDTO) {
        return memberServiceImpl.validateDuplicatedEmail(memberDTO);
    }

    @GetMapping("/api/logIn") // 로그인
    public MemberDTO logInForm(MemberDTO memberDTO) {
        securityConfig.accessToken(memberDTO);
        return memberServiceImpl.logIn(memberDTO);
    }

    @PostMapping("/api/withdrawal") // 회원삭제
    public boolean withdrawal(MemberDTO memberDTO) {
        return memberServiceImpl.withdrawal(memberDTO);
    }

    @PostMapping("/api/findPw/modifyPassword")
    public void forgotAndModifyPassword(MemberDTO memberDTO) {
        memberServiceImpl.forgotAndModifyPassword(memberDTO);
    }

}
