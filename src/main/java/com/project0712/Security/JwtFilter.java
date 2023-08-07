package com.project0712.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
import java.util.List;

// 매번 인증을 해야되기 때문에 OncePerRequestFilter로 처리
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;
    private final ModelMapper modelMapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken="";
        String refreshToken="";


        Cookie[] cookies = request.getCookies();
        Cookie cookie1 = new Cookie("error","");
        try{
            for (Cookie cookie : cookies) {
                cookie1 = cookie;
                switch (cookie.getName()) {
                    case "accessToken" -> accessToken=cookie.getValue();
                    case "refreshToken" -> refreshToken=cookie.getValue();
                }
            }
        }
        catch (NullPointerException e){
            log.error("NullPointerException",cookie1);
        }


//        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", accessToken);

        String username = "w";
        String password = "w3";

        //token
        if (accessToken == null || accessToken.equals("")) {
            log.error("No authorization");
            filterChain.doFilter(request, response);
        } else {
            boolean validateToken = tokenConfig.validateToken(accessToken);
            if (validateToken) {

                String map = modelMapper.map(MemberRole.USER, String.class);

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, List.of(new SimpleGrantedAuthority(map)));

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
                filterChain.doFilter(request, response);
            }
        }
    }
}
