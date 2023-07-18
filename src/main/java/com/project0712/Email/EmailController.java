package com.project0712.Email;

import com.project0712.Member.MemberDTO;
import com.project0712.Member.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final MemberServiceImpl memberServiceImpl;
    private final EmailServiceImpl emailServiceImpl;
    @PostMapping("/api/findId/sendEmail")  //id찾기에서 인증코드 보내기
    public String sendVerificationCodeToFindId(MemberDTO memberDTO) {
        //1. 가입 시의 이메일과 입력한 이메일이 일치하는지
        //2. 일치할 시 해당하는 이메일로 인증번호 부여
        //3. 부여한 인증번호와 입력한 인증번호 값이 일치하면 가입날짜와 함께 ID를 보여줌
        String email = memberServiceImpl.findEmail(memberDTO);
        emailServiceImpl.sendEmail(email);

        return email;

    }
    @PostMapping("/api/findId/sendEmailAndVerificationCode")
    public String findId(EmailDTO emailDTO) { //인증코드가 맞다면 이메일과 매핑되는 id를 뷰에 리턴함

        return emailServiceImpl.sendEmailAndVerificationCode(emailDTO);

    }


    @PostMapping("/api/findPw/sendEmailAndUserId") // Id와 이메일이 등록되어 있고 일치하면 인증코드보냄
    public List<String> sendVerificationCodeToResetPW(MemberDTO memberDTO) {

        List<String> idAndEmail = memberServiceImpl.findIdAndEmail(memberDTO);
        String id = idAndEmail.get(0);
        String email = idAndEmail.get(1);

        emailServiceImpl.sendEmail(email);

        return idAndEmail;

    }
    @PostMapping("/api/findPw/sendEmailAndVerificationCodeAndUserId")
    public List<String> findPW(EmailDTO emailDTO) {

        return emailServiceImpl.sendIdAndEmailAndVerificationCode(emailDTO);

    }
}

