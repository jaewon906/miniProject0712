package com.project0712.Security;

import com.project0712.Board.BoardDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenConfig tokenConfig;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http .csrf(csrf -> {
                    csrf.disable();
                })
                .cors(cors -> cors.disable())
                .formLogin(form -> form.disable())
                .authorizeHttpRequests(req -> {
                    req
                            .requestMatchers("/api/board/**").authenticated() // 글 관련 기능등 (crud)
                            .requestMatchers("/api/withdrawal").authenticated() //회원탈퇴
                            .anyRequest().permitAll(); //나머지 통신은 로그인을 하지않아도 됨
                });
//      앞서서 먼저 id와 pw로 인증을 진행했기 때문에 id pw 필터 앞에 Jwt 필터를 넣어서 필터로 인증을 진행
        http.addFilterBefore(new JwtFilter(tokenConfig, new ModelMapper()),UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}