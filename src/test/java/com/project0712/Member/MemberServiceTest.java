package com.project0712.Member;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class MemberServiceTest {
    private final MemberRepository memberRepository;
    @Test
    public void 중복회원가입방지() throws Exception{ // 매개변수를 붙히니까 안댐

        List<MemberEntity> qwer1234 = memberRepository.findAllByuserId("qwer1234");
        assertEquals("3",qwer1234.get(0).getUserId());


    }
    @Test
    public void 로그인_서비스() throws Exception{
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId("ploi9");
        memberDTO.setUserPassword("rkddkwl");

        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> allByUserId = memberRepository.findByuserId(memberEntity.getUserId());
        assertEquals(memberDTO.getUserId(),allByUserId.get().getUserId());

        assertEquals(memberDTO.getUserPassword(),allByUserId.get().getUserPassword());

       if(allByUserId.isPresent()){

            if(memberDTO.getUserPassword().equals(allByUserId.get().getUserPassword())){
               assertEquals("ploi9",MemberDTO.EntityToDTO(allByUserId.get()).getUserId());
            }
            else {

            }

        }
    }
}
