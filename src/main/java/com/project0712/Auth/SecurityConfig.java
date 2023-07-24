package com.project0712.Auth;

import com.project0712.Member.MemberDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;

@Component
public class SecurityConfig {

    public String jws(MemberDTO memberDTO) {
        AuthDTO authDTO = new AuthDTO();
        Key key = Keys.hmacShaKeyFor(authDTO.getSecretKeyToByte());
        return Jwts.builder()
                .claim("userId", memberDTO.getUserId())
                .claim("userNickname", memberDTO.getUserNickname())
                .claim("userEmail", memberDTO.getUserEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+10*60*1000))
                .signWith(key, SignatureAlgorithm.HS256) // (alg, secret_key)는 Deprecated됨
                .compact();

    }
}
