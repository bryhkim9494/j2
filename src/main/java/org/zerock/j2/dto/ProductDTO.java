package org.zerock.j2.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data // Lombok 어노테이션으로 모든 필드의 getter, setter 등을 자동으로 생성
@ToString // Lombok 어노테이션으로 toString() 메서드 자동 생성
@Builder // Lombok 어노테이션으로 빌더 패턴을 사용한 객체 생성 메서드 자동 생성
@AllArgsConstructor // Lombok 어노테이션으로 모든 필드를 사용하는 생성자 자동 생성
@NoArgsConstructor // Lombok 어노테이션으로 기본 생성자 자동 생성
public class ProductDTO {

    private Long pno; // 상품 번호
    private String pname; // 상품 이름
    private String pdesc; // 상품 설명
    private int price; // 상품 가격

    @Builder.Default
    private List<String> images = new ArrayList<>(); // 상품 이미지 파일 이름 목록, 기본적으로 빈 리스트로 초기화

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>(); // 등록하고 수정할 때 업로드된 파일 데이터를 수집하는 용도, 기본적으로 빈 리스트로 초기화
}
