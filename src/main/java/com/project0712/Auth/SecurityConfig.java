package com.project0712.Auth;

import com.project0712.Member.MemberDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class SecurityConfig {

    public Date expireDate(long num){
        return new Date(System.currentTimeMillis() + num);
    }

    public String accessToken(MemberDTO memberDTO) {
        Date currentTime =new Date(System.currentTimeMillis());
        long expireDate = 10*60*1000; //10분

        Key key = Keys.hmacShaKeyFor(AuthDTO.secretKeyToByte);

        return Jwts.builder()
                .setHeaderParam("type","accessToken")
                .claim("userId", memberDTO.getUserId())
                .claim("userNickname", memberDTO.getUserNickname())
                .claim("userEmail", memberDTO.getUserEmail())
                .setIssuer(memberDTO.getUserId())
                .setIssuedAt(currentTime)
                .setExpiration(expireDate(expireDate))
                .signWith(key, SignatureAlgorithm.HS256) // (alg, secret_key)는 Deprecated
                .compact();

    }

    public String refreshToken(MemberDTO memberDTO){
        Date currentTime =new Date(System.currentTimeMillis());
        long expireDate = 7L*24*3600*60*1000; //7일

        Key key = Keys.hmacShaKeyFor(AuthDTO.secretKeyToByte);

        return Jwts.builder()
                .setHeaderParam("type","refreshToken")
                .claim("userId", memberDTO.getUserId())
                .claim("userNickname", memberDTO.getUserNickname())
                .claim("userEmail", memberDTO.getUserEmail())
                .setIssuer(memberDTO.getUserId())
                .setIssuedAt(currentTime)
                .setExpiration(expireDate(expireDate))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
