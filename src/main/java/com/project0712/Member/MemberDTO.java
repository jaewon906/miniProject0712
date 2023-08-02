package com.project0712.Member;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {
    private Long id; //pk

    private String userId;// 아이디
    private String userPassword; // 비밀번호
    private String userNickname; //닉네임
    private String userEmail; //이메일
    private String userTel; //전화번호
    private String userAddress; //주소
    private String userSex; //성별
    private MemberRole role; //권한

    public static MemberDTO EntityToDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setId(memberEntity.getId());
        memberDTO.setUserId(memberEntity.getUserId());
        memberDTO.setUserPassword(memberEntity.getUserPassword());
        memberDTO.setUserNickname(memberEntity.getUserNickname());
        memberDTO.setUserEmail(memberEntity.getUserEmail());
        memberDTO.setUserTel(memberEntity.getUserTel());
        memberDTO.setUserAddress(memberEntity.getUserAddress());
        memberDTO.setUserSex(memberEntity.getUserSex());

        return memberDTO;
    }


}
