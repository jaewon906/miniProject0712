package com.project0712.Member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;


    @Override
    public void withdrawal(MemberDTO memberDTO) { //회원 탈퇴
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> allByUserId = memberRepository.findByuserId(memberEntity.getUserId());

        if (allByUserId.isPresent()) {
            // No EntityManager with actual transaction available for current thread 에러 -> @Transactional 어노테이션 붙히면 해결
            if (memberDTO.getUserPassword().equals(allByUserId.get().getUserPassword())) {
                memberRepository.deleteAllByUserId(allByUserId.get().getUserId());
            }

        }

    }

    @Override
    public MemberDTO logIn(MemberDTO memberDTO) { //로그인
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> allByUserId = memberRepository.findByuserId(memberEntity.getUserId());

        if (allByUserId.isPresent()) {

            if (memberDTO.getUserPassword().equals(allByUserId.get().getUserPassword())) {
                return MemberDTO.EntityToDTO(allByUserId.get());
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public void save(MemberDTO memberDTO) { // 회원가입
        //유일해야 하는것 : id, 닉네임, 이메일
        Optional<MemberEntity> foundUser = memberRepository.findByuserId
                (memberDTO.getUserId());

        if (foundUser.isEmpty()) {
            memberRepository.save(MemberEntity.DTOToEntity(memberDTO));
        }
    }

    @Override
    public String validateDuplicatedId(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> byuserId = memberRepository.findByuserId(memberEntity.getUserId());

        if(byuserId.isPresent()) return "아이디가 중복됩니다.";

        return null;
    }

    @Override
    public String validateDuplicatedNickname(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> byuserNickname = memberRepository.findByuserNickname(memberEntity.getUserNickname());

        if(byuserNickname.isPresent()) return "닉네임이 중복됩니다.";

        return null;
    }

    @Override
    public String validateDuplicatedEmail(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> byuserEmail = memberRepository.findByuserEmail(memberEntity.getUserEmail());

        if(byuserEmail.isPresent()) return "이메일이 중복됩니다.";

        return null;
    }
}

