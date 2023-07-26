package com.project0712.Member;

import com.project0712.Auth.TokenConfig;
import com.project0712.Auth.TokenDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final TokenConfig tokenConfig;

    @Override
    public boolean withdrawal(MemberDTO memberDTO) { //회원 탈퇴
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> allByUserId = memberRepository.findByuserId(memberEntity.getUserId());

        if (allByUserId.isPresent()) {
            // No EntityManager with actual transaction available for current thread 에러 -> @Transactional 어노테이션 붙히면 해결
            if (memberDTO.getUserPassword().equals(allByUserId.get().getUserPassword())) {
                memberRepository.deleteAllByUserId(allByUserId.get().getUserId());
                return true;
            }
            else return false;

        }
        else return false;
    }

    @Override
    public TokenDTO logIn(MemberDTO memberDTO) { //로그인
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        Optional<MemberEntity> allByUserId = memberRepository.findByuserId(memberEntity.getUserId());

        if (allByUserId.isPresent()) {

            if (memberDTO.getUserPassword().equals(allByUserId.get().getUserPassword())) {
                MemberDTO entityToDTO = MemberDTO.EntityToDTO(allByUserId.get());
                return tokenConfig.generateToken(entityToDTO);
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

    @Override
    public String findEmail(MemberDTO memberDTO) {
        Optional<MemberEntity> byuserEmail = memberRepository.findByuserEmail(memberDTO.getUserEmail());

        //해당하는 이메일이 존재한다면
        //이메일 보내기 기능을 담당하는 곳에 이메일 주소를 리턴함
        return byuserEmail.map(MemberEntity::getUserEmail).orElse(null);
    }

    @Override
    public List<String> findIdAndEmail(MemberDTO memberDTO) {
        List<String> idAndEmail = new ArrayList<>();
        Optional<MemberEntity> byuserId = memberRepository.findByuserId(memberDTO.getUserId());

        if(byuserId.isPresent()){
            if(byuserId.get().getUserEmail().equals(memberDTO.getUserEmail())){
                idAndEmail.add(byuserId.get().getUserId());
                idAndEmail.add(byuserId.get().getUserEmail());
                return idAndEmail;
            }
            else return null;
        }

        return null;
    }

    @Override
    public void forgotAndModifyPassword(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.DTOToEntity(memberDTO);
        memberRepository.save(memberEntity);
    }
}

