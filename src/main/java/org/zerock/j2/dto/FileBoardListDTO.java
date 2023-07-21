package org.zerock.j2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // Lombok 어노테이션으로 모든 필드의 getter, setter, toString 등을 자동으로 생성
@Builder // 빌더 패턴을 사용하여 객체 생성을 간편하게 지원하는 어노테이션
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 생성하는 어노테이션
@NoArgsConstructor // 파라미터 없는 기본 생성자를 자동으로 생성하는 어노테이션
@ToString // 객체의 문자열 표현을 자동으로 생성하는 어노테이션
public class FileBoardListDTO {
    private Long bno; // 게시글 번호
    private String title; // 제목
    private String uuid; // 파일의 UUID (Universally Unique Identifier)
    private String fname; // 파일명
}
