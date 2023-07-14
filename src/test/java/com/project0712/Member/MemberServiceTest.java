package com.project0712.Member;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

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
}
