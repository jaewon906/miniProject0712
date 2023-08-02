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
                            authorize // �ش��ϴ� url ��Ž� ���� �䱸
                                    .requestMatchers("/api/board/**").authenticated() // �� ���� ��ɵ� (crud)
                                    .requestMatchers("/api/withdrawal").authenticated() //ȸ��Ż��
                                    .anyRequest().permitAll(); //������ ����� �α����� �����ʾƵ� ��
                        })
                        .httpBasic(Customizer.withDefaults())
                        .formLogin(AbstractHttpConfigurer::disable) //disable -> ���Ǳ�� �α����� �ƴ� �ٸ� �α��� ���
                        .rememberMe(Customizer.withDefaults());
            } catch (Exception e) {

                throw new RuntimeException(e);
            }
        });
//      �ռ��� ���� id�� pw�� ������ �����߱� ������ id pw ���� �տ� Jwt ���͸� �־ ���ͷ� ������ ����
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
//            ���� ����
        }
        if (tokenState.equals("fail")) {
//            ���� ����
        }


//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
        return null;
    }




}