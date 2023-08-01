//package com.project0712.Auth;
//
//import com.project0712.Member.MemberDTO;
//import com.project0712.Member.MemberEntity;
//import com.project0712.Member.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import javax.management.relation.Role;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
////스프링 시큐리티에서 유저가 입력한 id, pw를 db와 비교해서 인증하는방식
//public class UserDetailServiceImpl implements UserDetailsService {
//    private final MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        Optional<MemberEntity> byUserId = memberRepository.findByuserId(userId);
//        Set<Role> roles = new HashSet<>();
//
//        if(byUserId.isPresent()){
//            MemberDTO memberDTO = MemberDTO.EntityToDTO(byUserId.get());
//
//            return new User(memberDTO.getUserId(),memberDTO.getUserPassword(),getAuthorities(roles));
//        }
//
//        return null;
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_ADMIN" + role.getRoleName()))
//                .collect(Collectors.toList());
//    }
//}
//
//
