package org.zerock.j2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok 어노테이션으로 모든 필드의 getter, setter 등을 자동으로 생성
@Builder // 빌더 패턴을 사용하여 객체 생성을 간편하게 지원하는 어노테이션
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 생성하는 어노테이션
@NoArgsConstructor // 파라미터 없는 기본 생성자를 자동으로 생성하는 어노테이션
public class MemberDTO {
    private String email; // 회원 이메일
    private String pw; // 회원 비밀번호
    private String nickname; // 회원 닉네임
    private boolean admin; // 회원 관리자 여부 (true: 관리자, false: 일반 사용자)

    private String accessToken; // 접근 토큰 (로그인 세션 유지를 위한 토큰)
    private String refreshToken; // 갱신 토큰 (새로운 접근 토큰을 발급받기 위한 토큰)

}
