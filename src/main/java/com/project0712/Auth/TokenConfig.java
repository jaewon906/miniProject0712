package com.project0712.Auth;

import com.project0712.Member.MemberDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TokenConfig {

    public Date expireDate(long num) {
        return new Date(System.currentTimeMillis() + num);
    }


    public TokenDTO generateToken(MemberDTO memberDTO) {
        Date currentTime = new Date(System.currentTimeMillis());
        long accessTokenExpireDate = 60L * 60 * 1000; //60분
        long refreshTokenExpireDate = 7L * 24 * 60 * 60 * 1000; //7일

        Key key = Keys.hmacShaKeyFor(AuthDTO.secretKeyToByte);

        String accessToken = Jwts.builder()
                .setHeaderParam("type", "accessToken")
                .claim("userId", memberDTO.getUserId())
                .claim("userNickname", memberDTO.getUserNickname())
                .claim("userEmail", memberDTO.getUserEmail())
                .claim("userAddress", memberDTO.getUserAddress())
                .claim("userSex", memberDTO.getUserSex())
                .claim("userTel", memberDTO.getUserTel())
                .setIssuer(memberDTO.getUserId())
                .setIssuedAt(currentTime)
                .setExpiration(expireDate(accessTokenExpireDate))
                .signWith(key, SignatureAlgorithm.HS256) // (alg, secret_key)는 Deprecated
                .compact();

        String refreshToken = Jwts.builder()
                .setHeaderParam("type", "refreshToken")
                .setIssuedAt(currentTime)
                .setExpiration(expireDate(refreshTokenExpireDate))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenDTO.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean validateToken(Map<String, String> tokens) {

        Key key = Keys.hmacShaKeyFor(AuthDTO.secretKeyToByte);
        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        try {
            //서블릿 요청으로부터 가져온 토큰들에 있는 인증키와 해당 인증키와 비교해서 검증하는 과정
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }

        return false;
    }

    public String memberInfoFromJWT(TokenDTO tokenDTO) {

        String accessToken = tokenDTO.getAccessToken();
        String[] splitAccessToken = accessToken.split("\\.");
        String payload = splitAccessToken[1];

        return new String(Base64.getDecoder().decode(payload));

    }
}
