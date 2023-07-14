package com.project0712.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void save(MemberDTO memberDTO) { // 회원가입
        List<MemberEntity> foundUser = memberRepository.findAllByuserId
                                     (memberDTO.getUserId());
        //유일해야 하는것 : id, 닉네임, 이메일
        if (foundUser.size()==0) {
            MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
            memberRepository.save(memberEntity);
        }
    }
}
