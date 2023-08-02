package com.project0712.Security;

import com.project0712.Member.MemberDTO;
import com.project0712.Member.MemberEntity;
import com.project0712.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByuserId(username).
                orElseThrow(() -> new UsernameNotFoundException("Have no registered members"));

        MemberDTO memberDTO = MemberDTO.EntityToDTO(memberEntity);
        return SecurityMember.getMemberDetails(memberDTO);
    }
}
