package com.project0712.Security;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;
    private final ModelMapper modelMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}",authorization);

        String username="wodnjs";
        String password="asdf";

        //token
        if(authorization==null || authorization.equals("")){
            log.error("No authorization");
            filterChain.doFilter(request,response);
        }

        else{
            boolean validateToken = tokenConfig.validateToken(authorization);
            if (validateToken){

                String map = modelMapper.map(MemberRole.USER, String.class);

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, List.of(new SimpleGrantedAuthority(map)));

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
                filterChain.doFilter(request,response);
            }
        }
    }
}
