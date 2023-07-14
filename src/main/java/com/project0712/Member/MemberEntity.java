package com.project0712.Member;

import com.project0712.Common.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "memberInfo")
public class MemberEntity extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String userPassword;
    @Column(nullable = false)
    private String userNickname;
    @Column(nullable = false)
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
        memberEntity.setUserPassword(memberDTO.getUserPassword());
        memberEntity.setUserNickname(memberDTO.getUserNickname());
        memberEntity.setUserEmail(memberDTO.getUserEmail());
        memberEntity.setUserTel(memberDTO.getUserTel());
        memberEntity.setUserAddress(memberDTO.getUserAddress());
        memberEntity.setUserSex(memberDTO.getUserSex());

        return memberEntity;
    }
}
