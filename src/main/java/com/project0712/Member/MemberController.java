package com.project0712.Member;

import com.project0712.Security.TokenConfig;
import com.project0712.Security.TokenDTO;
import com.project0712.Common.CookieConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/")
public class MemberController {

    private final MemberServiceImpl memberServiceImpl;
    private final TokenConfig tokenConfig;
    private final CookieConfig cookieConfig;

    @PostMapping("signUp")
    public void signUp(MemberDTO memberDTO) {
        memberServiceImpl.save(memberDTO);
    }

    @GetMapping("signUp")
    public MemberDTO sendMemberInfoToFrontend(MemberDTO memberDTO) {
        return memberDTO;
    }

    @GetMapping("signUp/id")
    public String validateId(MemberDTO memberDTO) {
        return memberServiceImpl.validateDuplicatedId(memberDTO);
    }

    @GetMapping("signUp/nickname")
    public String validateNickname(MemberDTO memberDTO) {
        return memberServiceImpl.validateDuplicatedNickname(memberDTO);
    }

    @GetMapping("signUp/email")
    public String validateEmail(MemberDTO memberDTO) {
        return memberServiceImpl.validateDuplicatedEmail(memberDTO);
    }

    @GetMapping("logIn") // 로그인
    public boolean logInForm(MemberDTO memberDTO, HttpServletResponse response) {
        TokenDTO tokenDTO = memberServiceImpl.logIn(memberDTO);

        try { //이 배치로 코드를 작성한 이유는 사용자의 닉네임을 쿠키로 전달하기 전에 아이디 비번 일치해서 토큰이 정상적으로 발급될 때 과정을 거친 후 쿠키에 사용자 닉네임 전송
            Cookie accessToken = cookieConfig.setCookie(tokenDTO.getAccessToken(), "accessToken", true, "/", 3600);
            Cookie refreshToken = cookieConfig.setCookie(tokenDTO.getRefreshToken(), "refreshToken", true, "/", 7 * 24 * 3600);
            Cookie userNickname = cookieConfig.setCookie(tokenConfig.memberInfoFromJWT(tokenDTO).get(0), "userNickname", false, "/", 3600);
            Cookie userId = cookieConfig.setCookie(tokenConfig.memberInfoFromJWT(tokenDTO).get(1), "userId", false, "/", 3600);
            response.addCookie(accessToken);
            response.addCookie(refreshToken);
            response.addCookie(userNickname); // 사용자의 닉네임을 쿠키로 전달
            response.addCookie(userId);
            return true;

        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.", e);
        } catch (Exception e) {
            log.error("error", e);
        }
        return false;
    }

    @PostMapping("withdrawal") // 회원삭제
    public boolean withdrawal(MemberDTO memberDTO, HttpServletResponse response) {
        boolean isDeleted = memberServiceImpl.withdrawal(memberDTO);
        if(isDeleted){
            String[] cookieKey = {"accessToken", "refreshToken", "userId", "userNickname"};
            cookieConfig.deleteCookie(response, cookieKey);
            return true;
        }
        return false;
    }

    @PostMapping("findPw/modifyPassword")
    public void forgotAndModifyPassword(MemberDTO memberDTO) {
        memberServiceImpl.forgotAndModifyPassword(memberDTO);
    }

    @PostMapping("logOut")
    public void logOut(HttpServletResponse response) {
        String[] cookieKey = {"accessToken", "refreshToken", "userId", "userNickname"};
        cookieConfig.deleteCookie(response, cookieKey);
    }


}
