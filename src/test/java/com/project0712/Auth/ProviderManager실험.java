package com.project0712.Auth;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
public class ProviderManager실험 implements AuthenticationManager {
    @Test
    public void 시큐리티컨텍스트구현해보기() {
        SecurityContext context = SecurityContextHolder.createEmptyContext(); // 빈 객체 생성
        Authentication authentication = new TestingAuthenticationToken("박재원", "1234", "ROLE_USER"); //인증 토큰 만듬

        context.setAuthentication(authentication); //코인을 컨텍스트 안에 넣음

        SecurityContextHolder.setContext(context); //코인을 넣은 컨텍스트를 컨텍스트홀더에 넣음

        SecurityContext result = SecurityContextHolder.getContext(); //[Principal=박재원, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[ROLE_USER]]]>


    }

    @Override
    @Test
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SecurityContext result = SecurityContextHolder.getContext();

        Authentication authentication1 = result.getAuthentication();

        assertEquals("d", authentication1);
        ProviderManager providerManager = new ProviderManager(); // AuthenticationProvider 여러개에 위임할 수 있음
        return authentication;
    }

    @Test
    public void authenticationManager구현해보기(AuthenticationManagerBuilder managerBuilder,ProviderManager provider) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //홀더에 담은 코인 빼오기
        List<AuthenticationProvider> providers = provider.getProviders();
        AuthenticationProvider first = providers.stream().findFirst().get();

        AuthenticationManager authenticationManager = managerBuilder.authenticationProvider(first).build();

    }


}

class UserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
//       아이디를 받아서 존재할 때 여부  로직을 구현함
        User user = new User("아디", "비번",authorities);
        authorities.stream();
        return null;
    }
}
