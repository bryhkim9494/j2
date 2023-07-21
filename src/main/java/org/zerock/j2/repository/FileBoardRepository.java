package org.zerock.j2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.j2.entity.FileBoard;
import org.zerock.j2.repository.search.FileBoardSearch;

public interface FileBoardRepository extends JpaRepository<FileBoard, Long>, FileBoardSearch {

    @EntityGraph(attributePaths = {"images"}) // @EntityGraph: JPA 엔티티 그래프 기능을 사용하여 지정된 속성 경로(attributePaths)에 해당하는 연관 엔티티들을 동시에 로딩한다.
    @Query("select b from FileBoard b where b.bno = :bno") // @Query: 사용자 정의 쿼리를 지정한다.
    FileBoard selectOne(@Param("bno") Long bno); // @Param: 쿼리에 바인딩되는 매개변수의 이름을 명시한다.
}
