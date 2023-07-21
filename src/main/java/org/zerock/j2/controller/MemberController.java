package org.zerock.j2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.MemberDTO;
import org.zerock.j2.service.MemberService;
import org.zerock.j2.service.SocialService;
import org.zerock.j2.util.JWTUtil;

import java.util.Map;

@RestController // 이 클래스를 REST 컨트롤러로 지정
@CrossOrigin // 다른 도메인으로부터의 크로스 오리진 요청을 허용
@RequiredArgsConstructor // 필요한 인자를 가진 생성자를 자동으로 생성
@RequestMapping("/api/member/") // 이 컨트롤러의 모든 매핑 메서드의 기본 URL 지정
@Log4j2 // 로깅을 위한 Lombok 어노테이션
public class MemberController {

    private final MemberService memberService; // MemberService 인터페이스를 구현한 서비스 객체를 주입받음

    private final SocialService socialService; // SocialService 인터페이스를 구현한 서비스 객체를 주입받음

    private final JWTUtil jwtUtil; // JWT 토큰 생성과 검증을 위한 유틸리티 객체를 주입받음

    @GetMapping("kakao") // GET 요청으로 "/api/member/kakao" URL에 접근 시 실행되는 메서드
    public MemberDTO getAuthCode(String code) {

        log.info("-------------------------------------");
        log.info(code); // 받은 인증 코드를 로그로 출력

        String email = socialService.getKakaoEmail(code); // 카카오 인증 코드를 이용하여 이메일 정보를 가져옴

        MemberDTO memberDTO = memberService.getMemberWithEmail(email); // 이메일을 이용하여 해당 회원 정보를 조회하고 결과를 반환

        return memberDTO; // 조회된 회원 정보를 반환
    }

    @PostMapping("login") // POST 요청으로 "/api/member/login" URL에 접근 시 실행되는 메서드
    public MemberDTO login(@RequestBody MemberDTO memberDTO) {

        log.info("Parameter: " + memberDTO); // 받은 요청의 바디에 포함된 MemberDTO 객체의 정보를 로그로 출력

        try {
            Thread.sleep(2000); // 테스트를 위해 2초간 스레드를 일시정지
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MemberDTO result = memberService.login(
                memberDTO.getEmail(),
                memberDTO.getPw()
        ); // 받은 이메일과 비밀번호를 이용하여 로그인을 시도하고 결과를 반환

        result.setAccessToken(
                jwtUtil.generate(
                        Map.of("email", result.getEmail()), 1)
        ); // 로그인에 성공하면 Access Token을 생성하여 MemberDTO 객체에 설정

        result.setRefreshToken(
                jwtUtil.generate(
                        Map.of("email", result.getEmail()), 60 * 24)
        ); // 로그인에 성공하면 Refresh Token을 생성하여 MemberDTO 객체에 설정

        log.info("Return: " + result); // 생성된 MemberDTO 객체의 정보를 로그로 출력

        return result; // 로그인 결과를 반환
    }

    @RequestMapping("refresh") // GET, POST 모두를 처리할 수 있는 "/api/member/refresh" URL에 접근 시 실행되는 메서드
    public Map<String, String> refresh(@RequestHeader("Authorization") String accessToken,
                                       String refreshToken) {

        log.info("Refresh.... access: " + accessToken); // Access Token 정보를 로그로 출력
        log.info("Refresh... refresh: " + refreshToken); // Refresh Token 정보를 로그로 출력

        // accessToken은 만료되었는지 확인

        // refreshToken은 만료되지 않았는지 확인

        Map<String, Object> claims = jwtUtil.validateToken(refreshToken); // 주어진 Refresh Token을 검증하여 클레임 정보를 반환

        return Map.of("accessToken", jwtUtil.generate(claims, 1),
                "refreshToken", jwtUtil.generate(claims, 60 * 24)); // 새로운 Access Token과 Refresh Token을 생성하여 반환

    }

}