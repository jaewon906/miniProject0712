package com.project0712.Email;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "EmailVerification")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userEmail;
    @Column
    private String userId;
    @Column
    private String verificationCode;

    public static EmailEntity dataToEntity(String email, String userId, String verificationCode) {
        EmailEntity emailEntity = new EmailEntity();

        emailEntity.setUserEmail(email);
        emailEntity.setUserId(userId);
        emailEntity.setVerificationCode(verificationCode);

        return emailEntity;
    }
    public static EmailEntity DTOToEntity(EmailDTO emailDTO) {
        EmailEntity emailEntity = new EmailEntity();

        emailEntity.setUserEmail(emailDTO.getUserEmail());
        emailEntity.setUserId(emailDTO.getUserId());
        emailEntity.setVerificationCode(emailDTO.getVerificationCode());

        return emailEntity;
    }
}
