package org.zerock.j2.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class JWTTests {
    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void testCreate() {
        Map<String, Object> claim = Map.of("email", "user00@aaa.com");
        String jwtStr = jwtUtil.generate(claim, 10);
        System.out.println(jwtStr);

    }

    @Test
    public void testToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InVzZXIwMEBhYWEuY29tIiwiaWF0IjoxNjg5NzQ0Mzc5LCJleHAiOjE2ODk3NDQ5Nzl9.0T_3Uxe15NfLEc6guM2zCVJclc9FeYVPifGF_inWEhw";
        try {

            jwtUtil.validateToken(token);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
