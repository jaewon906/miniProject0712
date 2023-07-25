package com.project0712.Member;

import com.project0712.Auth.TokenConfig;
import com.project0712.Auth.TokenDTO;
import com.project0712.Common.CookieConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
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
    public void logInForm(MemberDTO memberDTO, HttpServletResponse response) {
        TokenDTO tokenDTO = memberServiceImpl.logIn(memberDTO);
        try{ //이 배치로 코드를 작성한 이유는 사용자의 닉네임을 쿠키로 전달하기 전에 아이디 비번 일치해서 토큰이 정상적으로 발급될 때 과정을 거친 후 쿠키에 사용자 닉네임 전송
            Cookie accessToken = cookieConfig.setCookie(tokenDTO.getAccessToken(), "accessToken", true, "/", 3600);
            Cookie refreshToken = cookieConfig.setCookie(tokenDTO.getRefreshToken(), "refreshToken", true, "/", 7*24*3600);
            Cookie memberInfo = cookieConfig.setCookie(tokenConfig.memberInfoFromJWT(tokenDTO),"nickname",false,"/",3600);
            response.addCookie(accessToken);
            response.addCookie(refreshToken);
//            response.addCookie(memberInfo); // 사용자의 닉네임을 쿠키로 전달
        }catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
        }catch (Exception e){
            log.error("error",e);
        }

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
