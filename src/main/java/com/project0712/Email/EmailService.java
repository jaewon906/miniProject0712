package com.project0712.Email;



import com.project0712.Member.MemberDTO;

public interface EmailService {

    void sendEmail(String email);

    String sendEmailAndVerificationCode(EmailDTO emailDTO);

    MemberDTO sendIdAndEmailAndVerificationCode(EmailDTO emailDTO);


}
