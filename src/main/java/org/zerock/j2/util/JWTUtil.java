package org.zerock.j2.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JWTUtil {
    public static class CustomJWTException extends RuntimeException {
        public CustomJWTException(String msg) {
            super(msg);

        }
    }

    @Value("${org.zerock.jwt.secret}")
    private String key;

    // JWT 토큰 생성 메서드
    public String generate(Map<String, Object> claimMap, int min) { // jwt문자열을 만들려고 String으로 리턴타입함
        // map은 data , int는 지속시간

        //헤더
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");

        // claims (Payload)
        Map<String, Object> claims = new HashMap<>();
        claims.putAll(claimMap);
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) { // 예외 발생 시 예외를 잡아서 처리하지만, 이 예외를 발생시키는 상황은 발생하지 않도록 해야합니다.

        }

        // JWT 문자열 생성
        String jwtStr = Jwts.builder().setHeader(headers).setClaims(claims).setIssuedAt(Date.from(ZonedDateTime.now().toInstant())).setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key).compact();
        return jwtStr;
    }

    // JWT 토큰 유효성 검사 메서드
    public Map<String, Object> validateToken(String token) {
        Map<String, Object> claims = null;
        if (token == null) {
            throw new CustomJWTException("NullToken");
        }

        try {
            SecretKey key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));

            // 토큰 검증 및 claims 정보 얻기
            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (MalformedJwtException e) {
            throw new CustomJWTException("Malformed"); // JWT 문자열이 잘못되었을 때 예외 처리
        } catch (ExpiredJwtException e) {
            throw new CustomJWTException("Expired"); // 토큰의 유효 기간이 만료되었을 때 예외 처리
        } catch (InvalidClaimException e) {
            throw new CustomJWTException("Invalid"); // 토큰의 클레임(페이로드)이 유효하지 않을 때 예외 처리
        } catch (JwtException e) {
            throw new CustomJWTException(e.getMessage()); // 기타 JWT 예외 처리
        } catch (Exception e) {
            throw new CustomJWTException("Error"); // 기타 예외 처리
        }
        return claims;


    }

}
