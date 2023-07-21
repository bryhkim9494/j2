package org.zerock.j2.repository.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j2.dto.FileBoardListDTO;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.entity.FileBoard;
import org.zerock.j2.entity.QFileBoard;
import org.zerock.j2.entity.QFileBoardImage;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FileBoardSearchImpl extends QuerydslRepositorySupport implements FileBoardSearch {
    public FileBoardSearchImpl() { // // FileBoard 엔티티와 연결된 FileBoardSearchImpl 생성
        super(FileBoard.class);
    }

    @Override
    public PageResponseDTO<FileBoardListDTO> list(PageRequestDTO pageRequestDTO) {

        // Querydsl에서 사용할 별칭(alias)으로 QFileBoard, QFileBoardImage를 생성
        QFileBoard board = QFileBoard.fileBoard;
        QFileBoardImage boardImage = QFileBoardImage.fileBoardImage;

        // 파일 게시판과 첨부 이미지를 left join으로 연결하여 쿼리 시작
        JPQLQuery<FileBoard> query = from(board);
        query.leftJoin(board.images, boardImage); // 오늘 가장 중요한 코드

        // 첨부 이미지 중 첫 번째 이미지인 데이터만 조회하기 위한 조건 설정
        query.where(boardImage.ord.eq(0));
        // query.where(boardImage.ord.eq(0));

        int pageNum = pageRequestDTO.getPage() - 1 < 0 ? 0 : pageRequestDTO.getPage() - 1; // 페이지 정보 설정
        Pageable pageable = PageRequest.of(pageNum, pageRequestDTO.getSize(), Sort.by("bno").descending());

        this.getQuerydsl().applyPagination(pageable, query); // 페이지 정보를 쿼리에 적용하여 페이징 처리

        JPQLQuery<FileBoardListDTO> listQuery = query.select( // 쿼리 결과를 DTO로 변환하여 조회
                Projections.bean(
                        FileBoardListDTO.class,
                        board.bno,
                        board.title,
                        boardImage.uuid,
                        boardImage.fname));

        // 쿼리 실행하여 DTO 목록과 총 결과 수 가져오기
        List<FileBoardListDTO> list = listQuery.fetch();
        Long totalCount = listQuery.fetchCount();

        return new PageResponseDTO<>(list, totalCount, pageRequestDTO); // 페이징 처리된 목록과 총 결과 수를 담은 PageResponseDTO 객체를 반환
    }
}
