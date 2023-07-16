package com.project0712.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void withdrawal(MemberDTO memberDTO) { //회원 탈퇴
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> allByUserId = memberRepository.findByuserId(memberEntity.getUserId());

        if(allByUserId.isPresent()){ // No EntityManager with actual transaction available for current thread 에러
            if(memberDTO.getUserPassword().equals(allByUserId.get().getUserPassword())){
                memberRepository.deleteAllByUserId(allByUserId.get().getUserId());
            }

        }

    }

    @Override
    public MemberDTO logIn(MemberDTO memberDTO) { //로그인
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> allByUserId = memberRepository.findByuserId(memberEntity.getUserId());

        if(allByUserId.isPresent()){

            if(memberDTO.getUserPassword().equals(allByUserId.get().getUserPassword())){
                return MemberDTO.EntityToDTO(allByUserId.get());
            }
            else{
                return null;
            }
        }
        return null;
    }

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
