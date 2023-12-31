package com.project0712.Security;

import com.project0712.Member.MemberDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityMember extends User {
    public SecurityMember(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
    }

    public static SecurityMember getMemberDetails(MemberDTO member) {
        return new SecurityMember(member.getUserId(),member.getUserPassword(),parseAuthorities(member.getRole()));
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(MemberRole role) {
        return Arrays.asList(role).stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toList());
    }

    public String getRole(){
        return getAuthorities().stream().findFirst().get().getAuthority();
    }
}
