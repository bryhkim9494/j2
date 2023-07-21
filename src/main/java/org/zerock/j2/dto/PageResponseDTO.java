package org.zerock.j2.dto;

import java.util.List;
import java.util.stream.IntStream;

import lombok.Data;

@Data // Lombok 어노테이션으로 모든 필드의 getter, setter 등을 자동으로 생성
public class PageResponseDTO<E> {

    private List<E> dtoList; // 요청된 페이지에 포함된 데이터 목록
    private long totalCount; // 전체 데이터 개수
    private List<Integer> pageNums; // 화면에 보여줄 페이지 번호 목록
    private boolean prev, next; // 이전 페이지, 다음 페이지 존재 여부
    private PageRequestDTO requestDTO; // 페이지 요청 정보 (요청 페이지 번호, 페이지 크기, 검색 조건 등)
    private int page, size, start, end; // 현재 페이지 번호, 페이지 크기, 시작 페이지 번호, 끝 페이지 번호

    // 생성자
    public PageResponseDTO(List<E> dtoList, long totalCount, PageRequestDTO pageRequestDTO) {
        this.dtoList = dtoList; // 요청된 페이지의 데이터 목록
        this.totalCount = totalCount; // 전체 데이터 개수
        this.requestDTO = pageRequestDTO; // 페이지 요청 정보
        this.page = pageRequestDTO.getPage(); // 현재 페이지 번호 설정
        this.size = pageRequestDTO.getSize(); // 페이지 크기 설정
        int tempEnd = (int) (Math.ceil(page / 10.0) * 10); // 현재 페이지를 기준으로 끝 페이지 번호 설정

        this.start = tempEnd - 9; // 시작 페이지 번호 설정
        this.prev = this.start != 1; // 이전 페이지 존재 여부 설정

        // 전체 페이지 개수 구하기 (전체 데이터 개수를 페이지 크기로 나눈 결과의 올림값)
        int realEnd = (int) (Math.ceil(totalCount / (double) size));

        // 끝 페이지 번호 설정 (끝 페이지 번호가 실제 전체 페이지 개수보다 클 경우, 끝 페이지 번호를 전체 페이지 개수로 설정)
        this.end = tempEnd > realEnd ? realEnd : tempEnd;

        this.next = (this.end * this.size) < totalCount; // 다음 페이지 존재 여부 설정

        // 시작 페이지부터 끝 페이지까지의 페이지 번호를 리스트로 생성하여 pageNums에 설정
        this.pageNums = IntStream.rangeClosed(start, end).boxed().toList();
    }

}
