package com.project0712.Auth;

import com.project0712.Board.BoardDTO;
import com.project0712.Member.MemberDTO;
import com.project0712.Member.MemberEntity;
import com.project0712.Member.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenConfig {

    private final MemberRepository memberRepository;

    public Date expireDate(long num) {
        return new Date(System.currentTimeMillis() + num);
    }


    public TokenDTO generateToken(MemberDTO memberDTO) { // 초기 토큰 생성
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


    public String TokenRegenerator(Map<String, String> tokens, BoardDTO boardDTO) { // 토큰 재생성
        // 엑세스토큰과 리프레쉬토큰 둘 다 유효하면 생성 x
        // 엑세스토큰이 만료되고 리프레쉬토큰이 유효하면 엑세스토큰 생성
        // 엑세스토큰 리프레쉬토큰 둘 다 무효하면 생성 x

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        boolean isAccessTokenValidate = validateToken(accessToken);
        boolean isRefreshTokenValidate = validateToken(refreshToken);

        Date currentTime = new Date(System.currentTimeMillis());
        long accessTokenExpireDate = 60L * 60 * 1000; //60분

        Key key = Keys.hmacShaKeyFor(AuthDTO.secretKeyToByte);

        Optional<MemberEntity> byUserId = memberRepository.findByuserId(boardDTO.getAuthor());

        if(byUserId.isPresent()){
            MemberDTO memberDTO = MemberDTO.EntityToDTO(byUserId.get());

            if(isAccessTokenValidate && isRefreshTokenValidate){
                log.info("access, refreshToken are valid");
                return "pass";
            }

            else if (!isAccessTokenValidate && isRefreshTokenValidate) {

                String regeneratedAccessToken = Jwts.builder()
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

                log.info("accessToken regenerate success");

                return regeneratedAccessToken;
            } else{
                log.error("fail to regenerate accessToken");

                return "fail";
            }
        }
        log.error("userInfo is not present");

        return "fail";
    }

    public boolean validateToken(String token) { //토큰 검증
        Key key = Keys.hmacShaKeyFor(AuthDTO.secretKeyToByte);
        try {
            //서블릿 요청으로부터 가져온 accessToken에 있는 인증키와 해당 인증키와 비교해서 검증하는 과정
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        } catch (Exception e) {
            log.info("Exception", e);
        }
        return false;
    }

    public List<String> memberInfoFromJWT(TokenDTO tokenDTO) {

        String accessToken = tokenDTO.getAccessToken();
        String[] splitAccessToken = accessToken.split("\\.");
        String payload = splitAccessToken[1];
        JSONObject jsonObject = new JSONObject(new String(Base64.getDecoder().decode(payload)));

        List<String> result = new ArrayList<>();
        result.add(jsonObject.getString("userNickname"));
        result.add(jsonObject.getString("userId"));
        return result;

    }
}
