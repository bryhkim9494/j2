package org.zerock.j2.service;

import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductDTO;
import org.zerock.j2.dto.ProductListDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface ProductService {
    // 상품 목록을 페이지 단위로 조회하는 메서드
    PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO);


    // 상품을 등록하는 메서드, 등록된 상품의 고유번호 반환
    Long register(ProductDTO productDTO);

    // 특정 상품의 상세 정보를 조회하는 메서드
    ProductDTO readOne(Long pno);

    // 특정 상품을 삭제하는 메서드
    void remove(Long pno);

    // 특정 상품의 정보를 수정하는 메서드
    void modify(ProductDTO productDTO);
}
