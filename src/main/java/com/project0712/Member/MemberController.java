package com.project0712.Member;

import com.project0712.Auth.TokenConfig;
import com.project0712.Auth.TokenDTO;
import com.project0712.Common.CookieConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;
    private final TokenConfig tokenConfig;
    private final CookieConfig cookieConfig;

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
    public String logInForm(MemberDTO memberDTO, HttpServletResponse response) {
        TokenDTO tokenDTO = memberServiceImpl.logIn(memberDTO);
        Cookie accessToken = cookieConfig.setCookie(tokenDTO.getAccessToken(), "accessToken", true, "/", 3600 / 60);
        Cookie refreshToken = cookieConfig.setCookie(tokenDTO.getRefreshToken(), "refreshToken", true, "/", 7*24*3600);
        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        return tokenDTO.getAccessToken();

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
