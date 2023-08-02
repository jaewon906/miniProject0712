package com.project0712.Security;

import com.project0712.Board.BoardDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Component
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfiguration {
    private final TokenConfig tokenConfig;

    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.csrf(CSRF_protection -> {
            try {
                CSRF_protection
                        .disable()
                        .authorizeHttpRequests(authorize -> {
                            authorize // 해당하는 url 통신시 인증 요구
                                    .requestMatchers("/api/board/**").authenticated() // 글 관련 기능등 (crud)
                                    .requestMatchers("/api/withdrawal").authenticated() //회원탈퇴
                                    .anyRequest().permitAll(); //나머지 통신은 로그인을 하지않아도 됨
                        })
                        .httpBasic(Customizer.withDefaults())
                        .formLogin(AbstractHttpConfigurer::disable) //disable -> 세션기반 로그인이 아닌 다른 로그인 방식
                        .rememberMe(Customizer.withDefaults());
            } catch (Exception e) {

                throw new RuntimeException(e);
            }
        });
//      앞서서 먼저 id와 pw로 인증을 진행했기 때문에 id pw 필터 앞에 Jwt 필터를 넣어서 필터로 인증을 진행
        http.addFilterBefore(new JwtFilter(),UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    public AuthenticationManager authenticationManager() {
        return null;
    }


    public Authentication authentication(HttpServletRequest request, BoardDTO boardDTO) {
        Cookie[] cookies = request.getCookies();
        Map<String, String> tokens = new HashMap<>();
        for (Cookie cookie : cookies) {
            switch (cookie.getName()) {
                case "accessToken", "refreshToken" -> tokens.put(cookie.getName(), cookie.getValue());
            }
        }

        String tokenState = tokenConfig.TokenRegenerator(tokens, boardDTO);

        if (tokenState.equals("pass")) {
//            인증 성공
        }
        if (tokenState.equals("fail")) {
//            인증 실패
        }


//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
        return null;
    }




}