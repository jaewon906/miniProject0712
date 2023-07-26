package com.project0712.Member;

import com.project0712.Auth.TokenConfig;
import com.project0712.Auth.TokenDTO;
import com.project0712.Common.CookieConfig;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;


import static org.junit.jupiter.api.Assertions.*;


import java.io.UnsupportedEncodingException;
import java.util.Optional;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Slf4j
public class MemberServiceTest {
    private final MemberRepository memberRepository;
    private final TokenConfig tokenConfig;
    private final CookieConfig cookieConfig;

    @Test
    public void 중복회원가입방지() { // 매개변수를 붙히니까 안댐

        //유일해야 하는것 : id, 닉네임, 이메일
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId("qwer1");
        memberDTO.setUserNickname("qw");
        memberDTO.setUserEmail("plpl@ba");

        Optional<MemberEntity> foundUser = memberRepository.findByuserId
                (memberDTO.getUserId());

        if (foundUser.isEmpty()) {
            memberRepository.save(MemberEntity.DTOToEntity(memberDTO));
        }
    }

    @Test
    public void 아이디_닉네임_이메일_중복체크() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId("qwer");
        memberDTO.setUserNickname("qqq");
        memberDTO.setUserEmail("1@1");

        assertEquals("qwer", memberRepository.findByuserId(memberDTO.getUserId()).get().getUserId());
        assertEquals("qqq", memberRepository.findByuserNickname(memberDTO.getUserNickname()).get().getUserNickname());
        assertEquals("1@1", memberRepository.findByuserEmail(memberDTO.getUserEmail()).get().getUserEmail());


    }


    @Test
    public void 로그인_서비스() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId("ploi9");
        memberDTO.setUserNickname("1111");
        memberDTO.setUserEmail("1");

        tokenConfig.generateToken(memberDTO);


//        assertEquals("", token);
        assertEquals("", tokenConfig.generateToken(memberDTO));

        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> allByUserId = memberRepository.findByuserId(memberEntity.getUserId());
        assertEquals(memberDTO.getUserId(), allByUserId.get().getUserId());

        assertEquals(memberDTO.getUserPassword(), allByUserId.get().getUserPassword());

        if (allByUserId.isPresent()) {

            if (memberDTO.getUserPassword().equals(allByUserId.get().getUserPassword())) {
                assertEquals("ploi9", MemberDTO.EntityToDTO(allByUserId.get()).getUserId());
            } else {

            }

        }
    }

    @Test
    @Transactional
    public void 회원탈퇴() { //회원 탈퇴
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId("qwer1");
        memberDTO.setUserPassword("1111");
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> allByUserId = memberRepository.findByuserId(memberEntity.getUserId());

        if (allByUserId.isPresent()) {
            // No EntityManager with actual transaction available for current thread 에러 -> @Transactional 어노테이션 붙히면 해결
            if (memberDTO.getUserPassword().equals(allByUserId.get().getUserPassword())) {
                log.info("지워졌다");
                memberRepository.deleteAllByUserId(allByUserId.get().getUserId());
            } else log.info("아이디 비번이 일치하지 않는다");

        } else log.info("해당하는 아이디가 없다");
    }

    @Test
    public void 로그아웃() throws UnsupportedEncodingException {
        TokenDTO tokenDTO = TokenDTO.builder()
                .grantType("Bearer")
                .accessToken("eyJ0eXBlIjoiYWNjZXNzVG9rZW4iLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJxd2VyMSIsInVzZXJOaWNrbmFtZSI6IuyerOybkOyUqCIsInVzZXJFbWFpbCI6InBsb2k5QG5hdmVyLmNvbSIsInVzZXJBZGRyZXNzIjoiMTMxMyIsInVzZXJTZXgiOiLrgqjsnpAiLCJ1c2VyVGVsIjoiMDEwLTIyNDAtMTkyOCIsImlzcyI6InF3ZXIxIiwiaWF0IjoxNjkwMjk0NTk2LCJleHAiOjE2OTAyOTQ1OTd9._o0Eu2fgDF8cvwh6MKAezFvITUMzLvwA88txDE4ssnI")
                .refreshToken("eyJ0eXBlIjoiYWNjZXNzVG9rZW4iLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJxd2VyMSIsInVzZXJOaWNrbmFtZSI6IuyerOybkOyUqCIsInVzZXJFbWFpbCI6InBsb2k5QG5hdmVyLmNvbSIsInVzZXJBZGRyZXNzIjoiMTMxMyIsInVzZXJTZXgiOiLrgqjsnpAiLCJ1c2VyVGVsIjoiMDEwLTIyNDAtMTkyOCIsImlzcyI6InF3ZXIxIiwiaWF0IjoxNjkwMjk0NTk2LCJleHAiOjE2OTAyOTQ1OTd9._o0Eu2fgDF8cvwh6MKAezFvITUMzLvwA88txDE4ssnI")
                .build();
        Cookie accessToken = cookieConfig.setCookie(tokenDTO.getAccessToken(), "accessToken", true, "/", 3600);
        Cookie refreshToken = cookieConfig.setCookie(tokenDTO.getRefreshToken(), "refreshToken", true, "/", 7 * 24 * 3600);
        Cookie userNickname = cookieConfig.setCookie(tokenConfig.memberInfoFromJWT(tokenDTO).get(0), "userNickname", false, "/", 3600);
        Cookie userId = cookieConfig.setCookie(tokenConfig.memberInfoFromJWT(tokenDTO).get(1), "userId", false, "/", 3600);

        assertEquals("",userNickname.getValue());
    }
}
