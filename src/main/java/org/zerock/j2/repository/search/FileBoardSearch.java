package org.zerock.j2.repository.search;

import org.zerock.j2.dto.FileBoardListDTO;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;

public interface FileBoardSearch {
    PageResponseDTO<FileBoardListDTO> list(PageRequestDTO pageRequestDTO); // 파일 게시판 목록을 페이징하여 조회하는 메서드

    // pageRequestDTO 페이징 요청 정보를 담고 있는 DTO 객체

    // 페이징 처리된 파일 게시판 목록과 총 결과 수를 담고 있는 PageResponseDTO 객체
}
