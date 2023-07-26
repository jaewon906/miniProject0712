package com.project0712.Auth;

import com.project0712.Member.MemberDTO;
import com.project0712.Member.MemberEntity;
import com.project0712.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
//������ ��ť��Ƽ���� ������ �Է��� id, pw�� db�� ���ؼ� �����ϴ¹��
public class UserDetailServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<MemberEntity> byUserId = memberRepository.findByuserId(userId);
        Set<Role> roles = new HashSet<>();

        if(byUserId.isPresent()){
            MemberDTO memberDTO = MemberDTO.EntityToDTO(byUserId.get());

            return new User(memberDTO.getUserId(),memberDTO.getUserPassword(),getAuthorities(roles));
        }

        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());
    }
}
