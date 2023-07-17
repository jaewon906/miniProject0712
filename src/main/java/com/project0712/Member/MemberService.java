package com.project0712.Member;

public interface MemberService {
    void save(MemberDTO memberDTO);

    MemberDTO logIn(MemberDTO memberDTO);

    void withdrawal(MemberDTO memberDTO);

    String validateDuplicatedId(MemberDTO memberDTO);
    String validateDuplicatedNickname(MemberDTO memberDTO);
    String validateDuplicatedEmail(MemberDTO memberDTO);

}
