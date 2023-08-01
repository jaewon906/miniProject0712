package com.project0712.Auth;

import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;


@NoArgsConstructor
@SpringBootTest
public class 시큐리티컨텍스트에대해 {
    @Test
    public void 테스트1() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new TestingAuthenticationToken("박재원", "1234","ROLE_USER" );

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        SecurityContext result = SecurityContextHolder.getContext();

        assertEquals("context",result);
    }

}
