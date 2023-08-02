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

// 매번 인증을 해야되기 때문에 OncePerRequestFilter로 처리
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

        //토큰꺼내기

        //Username을 토큰에서 꺼내오기
        String userName = "";
        Key secretKey = Keys.hmacShaKeyFor(SecretKey.secretKeyToByte);

        //권한 부여
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName,"", List.of(new SimpleGrantedAuthority("USER")));

        //Detail을 넣어준다.
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request,response);
    }
}
