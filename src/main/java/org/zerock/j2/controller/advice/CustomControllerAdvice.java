package org.zerock.j2.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zerock.j2.service.MemberServiceImpl;

import java.util.Map;

@RestControllerAdvice // 모든 @Controller 또는 @RestController에서 발생하는 예외를 처리하는 전역 컨트롤러 어드바이스 클래스로 지정
@Log4j2 // 로깅을 위한 Lombok 어노테이션
public class CustomControllerAdvice {
    @ExceptionHandler(MemberServiceImpl.MemberLoginException.class) // MemberServiceImpl.MemberLoginException 예외를 처리하는 핸들러 메서드 지정
    public ResponseEntity<Map<String, String>> handleException(MemberServiceImpl.MemberLoginException e) {
        log.info("-----------------------------------------------------");

        log.info(e.getMessage()); // 예외 메시지를 로그로 출력
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("errorMsg", "Login Fail")); // 예외 발생 시 HTTP 상태 코드 200 OK와 메시지를 반환
    }
}
