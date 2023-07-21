package org.zerock.j2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.MemberCartDTO;
import org.zerock.j2.service.MemberCartService;

import java.util.List;

@RestController // 이 클래스를 REST 컨트롤러로 지정
@RequiredArgsConstructor // 필요한 인자를 가진 생성자를 자동으로 생성
@Log4j2 // 로깅을 위한 Lombok 어노테이션
@CrossOrigin // 다른 도메인으로부터의 크로스 오리진 요청을 허용
@RequestMapping("/api/cart/") // 이 컨트롤러의 모든 매핑 메서드의 기본 URL 지정
public class MemberCartController {
    private final MemberCartService cartService; // MemberCartService 인터페이스를 구현한 서비스 객체를 주입받음

    @PostMapping("add") // POST 요청으로 "/api/cart/add" URL에 접근 시 실행되는 메서드
    public List<MemberCartDTO> add(@RequestBody MemberCartDTO memberCartDTO) {
        log.info("param" + memberCartDTO); // 받은 요청의 바디에 포함된 MemberCartDTO 객체의 정보를 로그로 출력
        return cartService.addCart(memberCartDTO); // cartService의 addCart 메서드 호출하여 요청의 내용을 처리하고 결과를 반환
    }

    @GetMapping("{email}") // GET 요청으로 "/api/cart/{email}" URL에 접근 시 실행되는 메서드
    public List<MemberCartDTO> get(@PathVariable("email") String email) {
        return cartService.getCart(email); // cartService의 getCart 메서드 호출하여 주어진 이메일에 해당하는 카트 정보를 조회하고 결과를 반환
    }
}
