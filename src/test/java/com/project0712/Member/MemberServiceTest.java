package com.project0712.Member;

import com.project0712.Board.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;


import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class MemberServiceTest {
    private final MemberRepository memberRepository;

    @Test
    public void 중복회원가입방지() throws Exception { // 매개변수를 붙히니까 안댐

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
    public void 아이디_닉네임_이메일_중복체크() throws Exception {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId("qwer");
        memberDTO.setUserNickname("qqq");
        memberDTO.setUserEmail("1@1");

        assertEquals("qwer", memberRepository.findByuserId(memberDTO.getUserId()).get().getUserId());
        assertEquals("qqq", memberRepository.findByuserNickname(memberDTO.getUserNickname()).get().getUserNickname());
        assertEquals("1@1", memberRepository.findByuserEmail(memberDTO.getUserEmail()).get().getUserEmail());


    }


    @Test
    public void 로그인_서비스() throws Exception {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId("ploi9");
        memberDTO.setUserPassword("rkddkwl");

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


}
