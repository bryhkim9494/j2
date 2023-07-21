package org.zerock.j2.repository.search;

import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductListDTO;

public interface ProductSearch {

    // 상품 목록을 페이징하여 조회하는 메서드
    // @param : pageRequestDTO 페이징 요청 정보를 담고 있는 DTO 객체
    // @return : 페이징 처리된 상품 목록과 총 결과 수를 담고 있는 PageResponseDTO 객체
    PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO);

    // 리뷰 포함된 상품 목록을 페이징하여 조회하는 메서드
    // @param pageRequestDTO 페이징 요청 정보를 담고 있는 DTO 객체
    // @return 페이징 처리된 리뷰 포함 상품 목록과 총 결과 수를 담고 있는 PageResponseDTO 객체
    PageResponseDTO<ProductListDTO> listWithReview(PageRequestDTO pageRequestDTO);
}
