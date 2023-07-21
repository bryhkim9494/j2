package org.zerock.j2.controller.interceptor;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zerock.j2.util.JWTUtil;

import java.util.Map;

@Component // 스프링 빈으로 등록하는 어노테이션
@Log4j2 // 로깅을 위한 Lombok 어노테이션
@RequiredArgsConstructor // 생성자 인젝션을 위한 Lombok 어노테이션
public class JWTInterceptor implements HandlerInterceptor {
    private final JWTUtil jwtUtil; // JWTUtil 의존성 주입

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // OPTIONS 메서드의 요청은 허용
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        try {
            // "Authorization" 헤더에서 액세스 토큰을 가져옴
            String headerStr = request.getHeader("Authorization");

            if (headerStr == null || headerStr.length() < 7) {
                // 액세스 토큰이 없거나 올바르지 않은 경우 예외 발생
                throw new JWTUtil.CustomJWTException("NullToken");
            }

            // 액세스 토큰의 앞에 "Bearer " 접두어 제거
            String accessToken = headerStr.substring(7);
            // 액세스 토큰을 검증하여 토큰의 클레임(claim)을 가져옴
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);
            log.info("result: " + claims);
        } catch (Exception e) {
            // 예외가 발생한 경우 UNAUTHORIZED(401) 상태 코드와 메시지를 반환하여 인증 실패 처리
            response.setContentType("application/json");
            Gson gson = new Gson();
            String str = gson.toJson(Map.of("error", e.getMessage()));
            response.getOutputStream().write(str.getBytes());
            return false;
        }

        log.info("----------------------------------------------------------");
        log.info(handler);
        log.info("----------------------------------------------------------");
        log.info(jwtUtil);
        log.info("----------------------------------------------------------");
        log.info("----------------------------------------------------------");

        return true;
    }
}
