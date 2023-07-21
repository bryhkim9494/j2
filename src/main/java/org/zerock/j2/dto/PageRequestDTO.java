package org.zerock.j2.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data // Lombok 어노테이션으로 모든 필드의 getter, setter 등을 자동으로 생성
@ToString // Lombok 어노테이션으로 toString 메서드를 자동으로 생성
public class PageRequestDTO {

    private int page = 1; // 요청 페이지 번호, 기본값은 1
    private int size = 10; // 페이지 크기 (한 페이지에 보여줄 데이터 개수), 기본값은 10
    private String type; // 검색 조건 타입 (예: 제목, 내용 등)
    private String keyword; // 검색 키워드

    // 기본 생성자
    public PageRequestDTO() {
        this(1, 10);
    }

    // 페이지 번호와 페이지 크기를 받는 생성자
    public PageRequestDTO(int page, int size) {
        this(page, size, null, null);
    }

    // 페이지 번호, 페이지 크기, 검색 조건 타입, 검색 키워드를 받는 생성자
    public PageRequestDTO(int page, int size, String type, String keyword) {
        this.page = page <= 0 ? 1 : page; // 페이지 번호가 0보다 작거나 같으면 1로 초기화
        this.size = size <= 0 || size >= 100 ? 10 : size; // 페이지 크기가 0보다 작거나 같거나 100보다 크면 10으로 초기화
        this.type = type; // 검색 조건 타입 설정
        this.keyword = keyword; // 검색 키워드 설정
    }
}
