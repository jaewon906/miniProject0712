package com.project0712.Member;

import com.project0712.Common.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Member;

@Entity
@Getter
@Setter
@Table(name = "memberInfo")
public class MemberEntity extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private String userPassword;
    @Column(nullable = false, unique = true)
    private String userNickname;
    @Column(nullable = false, unique = true)
    private String userEmail;
    @Column(nullable = false)
    private String userTel;
    @Column(nullable = false)
    private String userAddress;
    @Column(nullable = false)
    private String userSex;

    public static MemberEntity DTOToEntity(MemberDTO memberDTO){

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setId(memberDTO.getId());
        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setUserPassword(BCrypt.hashpw(memberDTO.getUserPassword(),BCrypt.gensalt()));
        memberEntity.setUserNickname(memberDTO.getUserNickname());
        memberEntity.setUserEmail(memberDTO.getUserEmail());
        memberEntity.setUserTel(memberDTO.getUserTel());
        memberEntity.setUserAddress(memberDTO.getUserAddress());
        memberEntity.setUserSex(memberDTO.getUserSex());

        return memberEntity;
    }

    public static MemberEntity DTOToEntityOnlyUseDuplicateValidation(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setUserNickname(memberDTO.getUserNickname());
        memberEntity.setUserEmail(memberDTO.getUserEmail());

        return memberEntity;
    }
}
