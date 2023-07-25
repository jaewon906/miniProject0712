package com.project0712.Common;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CookieConfig {
    public Cookie setCookie(String token, String cookieKey, boolean setHttpOnly, String setPath, int setMaxAge) {
        Cookie cookie = new Cookie(cookieKey, token);
        cookie.setHttpOnly(setHttpOnly); //  httponly속성을 부여해서 xss공격을 막는다
        cookie.setPath(setPath);
        cookie.setMaxAge(setMaxAge);
        return cookie;
    }

    public Map<String,String> requestCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, String> result = new HashMap<>();

        for(Cookie cookie : cookies){
            switch (cookie.getName()) {
                case "accessToken" -> result.put("accessToken",cookie.getValue());
                case "refreshToken" -> result.put("refreshToken",cookie.getValue());
                default -> {
                }
            }
        }
        return result;
    }

}
