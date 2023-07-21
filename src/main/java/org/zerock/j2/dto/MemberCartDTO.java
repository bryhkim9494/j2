package org.zerock.j2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok 어노테이션으로 모든 필드의 getter, setter 등을 자동으로 생성
@Builder // 빌더 패턴을 사용하여 객체 생성을 간편하게 지원하는 어노테이션
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 생성하는 어노테이션
@NoArgsConstructor // 파라미터 없는 기본 생성자를 자동으로 생성하는 어노테이션
public class MemberCartDTO {
    private Long cno; // 장바구니 번호 (삭제하기 위해 필요한 값)

    private String email; // 회원 이메일
    private Long pno; // 상품 번호
}
