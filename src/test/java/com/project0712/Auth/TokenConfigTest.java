package com.project0712.Auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.security.Key;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
@Slf4j
public
class TokenConfigTest {
    @Test
    public void validateToken() {

        Key key = Keys.hmacShaKeyFor(TokenDTO.secretKeyToByte);
        String accessToken ="eyJ0eXBlIjoiYWNjZXNzVG9rZW4iLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJxd2VyIiwidXNlck5pY2tuYW1lIjoi7J6s7JuQ7JOwIiwidXNlckVtYWlsIjoicGxvaTlAbmF2ZXIuY29tIiwidXNlckFkZHJlc3MiOiIxMTExIiwidXNlclNleCI6IuuCqOyekCIsInVzZXJUZWwiOiIwMTAtMTIzNC0xMjM0IiwiaXNzIjoicXdlciIsImlhdCI6MTY5MDI3NjEyNSwiZXhwIjoxNjkwMjc2MTI2fQ.KCuNYm_uVX6U6AueARO8sr7DDKfUwKCAm6Vr4M6xqBA" ;
        String refreshToken = "eyJ0eXBlIjoicmVmcmVzaFRva2VuIiwiYWxnIjoiSFMyNTYifQ.eyJpYXQiOjE2OTAyNzM2NDEsImV4cCI6MTY5MDg3ODQ0MX0.j8uzqJVDPdyOFYHKXRVQPbgjfiCoHdb6kVvczYs0bPA";


        try {
            //서블릿 요청으로부터 가져온 토큰들에 있는 인증키와 해당 인증키와 비교해서 검증하는 과정
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            try{
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
                log.info("success");
            }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException err){
                log.info("Invalid Refresh Token", e);
            }
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }

    }
}
