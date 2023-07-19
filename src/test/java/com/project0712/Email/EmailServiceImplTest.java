package com.project0712.Email;

import com.project0712.Member.MemberEntity;
import com.project0712.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class EmailServiceImplTest {

    private final EmailRepository emailRepository;
    private final MemberRepository memberRepository;
    @Test
    public void DB저장이_되니() throws Exception {
        EmailDTO emailDTO = new EmailDTO();
        String email="email";
        String matchedId="matchedId";
        String verificationCode="verificationCode";
        EmailEntity emailEntity = EmailEntity.dataToEntity(email, matchedId, verificationCode);
        emailRepository.save(emailEntity);
        List<EmailEntity> all = emailRepository.findAll();

        assertEquals(1,all.size());

    }
    @Test
    public void 비번찾기때서비스() throws Exception {
        List<String> idAndEmail = new ArrayList<>();
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setId(1L);
        emailDTO.setUserId("qwer1234");
        emailDTO.setUserEmail("ploi9@naver.com");
        emailDTO.setVerificationCode("62514350");
        EmailEntity emailEntity = EmailEntity.DTOToEntity(emailDTO);
        Optional<EmailEntity> byuserEmail = emailRepository.findByuserEmail(emailEntity.getUserEmail()); //해당하는 이메일 찾기

        Optional<MemberEntity> byuserEmail1 = memberRepository.findByuserEmail(emailDTO.getUserEmail());
        assertEquals(emailEntity.getId(),byuserEmail1.get().getId());

        assertThat(byuserEmail.get().getUserEmail()).isEqualTo(emailDTO.getUserEmail());
        assertThat(byuserEmail.get().getUserId()).isEqualTo(emailDTO.getUserId());
        assertThat(byuserEmail.get().getVerificationCode()).isEqualTo(emailDTO.getVerificationCode());

        if (byuserEmail.isPresent() && byuserEmail.get().getUserId().equals(emailEntity.getUserId())) { //이메일이 존재하고 ID가 일치할 때
            if (byuserEmail.get().getVerificationCode().equals(emailEntity.getVerificationCode())) { //위 조건 + 인증번호가 만족될때

                idAndEmail.add(byuserEmail.get().getUserId());
                idAndEmail.add(byuserEmail.get().getUserEmail());
                assertEquals("qwer12342",idAndEmail.get(0));
                assertEquals("ploi9@naver.com",idAndEmail.get(1));
                emailRepository.deleteByUserEmail(byuserEmail.get().getUserEmail());

            }
        }
    }
}
