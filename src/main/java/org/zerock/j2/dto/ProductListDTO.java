package org.zerock.j2.dto;

import lombok.Data;

@Data
public class ProductListDTO {
    private Long pno; // 상품 번호
    private String pname; // 상품 이름
    private int price; // 상품 가격
    private String fname; // 상품 이미지 파일 이름
    private Long reviewCnt; // 상품에 대한 리뷰 개수
    private double reviewAvg; // 상품에 대한 리뷰 평균 평점
}
