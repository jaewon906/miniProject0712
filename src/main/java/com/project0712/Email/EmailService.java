package com.project0712.Email;



import java.util.List;

public interface EmailService {

    void sendEmail(String email);

    String sendEmailAndVerificationCode(EmailDTO emailDTO);

    List<String> sendIdAndEmailAndVerificationCode(EmailDTO emailDTO);


}
