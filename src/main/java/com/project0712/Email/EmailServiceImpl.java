package com.project0712.Email;


import com.project0712.Member.MemberDTO;
import com.project0712.Member.MemberEntity;
import com.project0712.Member.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j // 로깅(logging)기능
@Component
@RequiredArgsConstructor
@Transactional
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final EmailRepository emailRepository;

    @Override
    public void sendEmail(String email) { //해당하는 이메일로 인증코드를 보냄
//        MimeMessage는 javaMail API에서 이메일을 나타내는 클래스
//        javaMailSender는 이메일을 보내는데 사용되는 메일 전송 작업을 추상화함.
//        MimeMessageHelper 생성자의 매개변수 중 true는 멀티파트(사진 동영상을 첨부할 수 있는)형식을 지원여부를 묻는다.

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String matchedId = matcherIdAndEmail(email); //이메일에 해당하는 id
        String verificationCode = makeVerificationCode(); //인증번호 만들기

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "EUC-KR");
            helper.setTo(email); //메일 수신자.
            helper.setSubject("[Company]인증코드 입니다."); //메일 제목
            helper.setText("인증코드는 " + verificationCode + " 입니다."); //메일 내용
            javaMailSender.send(mimeMessage);
            log.info("성공");

            EmailEntity emailEntity = EmailEntity.dataToEntity(email, matchedId, verificationCode);
            emailRepository.save(emailEntity);
        } catch (MessagingException e) {
            log.error("실패");
        }

    }

    private String matcherIdAndEmail(String email) { //email에 해당하는 id찾기
        Optional<MemberEntity> byuserEmail = memberRepository.findByuserEmail(email);
        return byuserEmail.map(MemberEntity::getUserId).orElse(null);
    }

    private String makeVerificationCode() { // 인증번호 만들기
        StringBuilder verificationCode = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            verificationCode.append((int) (Math.floor(Math.random() * 10.0)));
        }
        return verificationCode.toString();
    }

    @Override
    public String sendEmailAndVerificationCode(EmailDTO emailDTO) { //id 찾기
        EmailEntity emailEntity = EmailEntity.DTOToEntity(emailDTO);
        Optional<EmailEntity> byuserEmail = emailRepository.findByuserEmail(emailEntity.getUserEmail());

        if (byuserEmail.isPresent()) {
            if (byuserEmail.get().getVerificationCode().equals(emailEntity.getVerificationCode())) {
                emailRepository.deleteByUserEmail(byuserEmail.get().getUserEmail());
                return byuserEmail.get().getUserId();
            } else return null;
        } else return null;
    }

    @Override
    public MemberDTO sendIdAndEmailAndVerificationCode(EmailDTO emailDTO) {
        EmailEntity emailEntity = EmailEntity.DTOToEntity(emailDTO);
        Optional<EmailEntity> ByuserEmail = emailRepository.findByuserEmail(emailEntity.getUserEmail()); //해당하는 이메일 찾기
        MemberEntity memberEntity = memberRepository.findByuserEmail(ByuserEmail.get().getUserEmail()).get(); //해당하는 이메일의 PK값 찾기


        if (ByuserEmail.get().getUserId().equals(emailEntity.getUserId())) { //이메일이 존재하고 ID가 일치할 때
            if (ByuserEmail.get().getVerificationCode().equals(emailEntity.getVerificationCode())) { //위 조건 + 인증번호가 만족될때

                MemberDTO memberDTO = MemberDTO.EntityToDTO(memberEntity);

                emailRepository.deleteByUserEmail(ByuserEmail.get().getUserEmail());
                return memberDTO;
            }
        }
        return null;
    }
}
