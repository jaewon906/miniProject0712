package com.project0712.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfiguration {
    private final UserDetailsService userDetailsService;
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http, AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        http.csrf(CSRF_protection -> {
            try {
                CSRF_protection.disable().authorizeHttpRequests(authorize -> {
                    authorize// �ش��ϴ� url ��Ž� ���� �䱸
                            .requestMatchers("/api/board/**").authenticated() // �� ���� ��ɵ� (crud)
                            .requestMatchers("/api/board").authenticated() // �ۿ���
                            .requestMatchers("/api/withdrawal").authenticated() //ȸ��Ż��
                            .anyRequest().permitAll(); //������ ����� �α����� �����ʾƵ� ?
                }).formLogin(form -> {
                        form
                            .loginPage("http://localhost:3000/logIn")
                            .loginProcessingUrl("http://localhost:3000/logIn")
                            .successForwardUrl("/")
                            .failureForwardUrl("http://localhost:3000/logIn")
                            .permitAll();
                }).rememberMe(Customizer.withDefaults());
            } catch (Exception e) {

                throw new RuntimeException(e);
            }
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
