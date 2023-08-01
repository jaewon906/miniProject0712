package com.project0712.Member;

import com.project0712.Auth.TokenDTO;

import java.util.List;

public interface MemberService {
    void save(MemberDTO memberDTO);

    TokenDTO logIn(MemberDTO memberDTO);

    boolean withdrawal(MemberDTO memberDTO);

    String validateDuplicatedId(MemberDTO memberDTO);
    String validateDuplicatedNickname(MemberDTO memberDTO);
    String validateDuplicatedEmail(MemberDTO memberDTO);
    String findEmail(MemberDTO memberDTO);

    List<String> findIdAndEmail(MemberDTO memberDTO);

    void forgotAndModifyPassword(MemberDTO memberDTO);

}
