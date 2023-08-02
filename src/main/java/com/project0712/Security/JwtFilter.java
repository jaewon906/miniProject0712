package com.project0712.Security;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.List;

// �Ź� ������ �ؾߵǱ� ������ OncePerRequestFilter�� ó��
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}",authorization);

        //token
        if(authorization==null || !authorization.startsWith("Bearer ")){
            log.error("authorization is null");
            filterChain.doFilter(request,response);
            return;
        }

        //��ū������

        //Username�� ��ū���� ��������
        String userName = "";
        Key secretKey = Keys.hmacShaKeyFor(SecretKey.secretKeyToByte);

        //���� �ο�
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName,"", List.of(new SimpleGrantedAuthority("USER")));

        //Detail�� �־��ش�.
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request,response);
    }
}
