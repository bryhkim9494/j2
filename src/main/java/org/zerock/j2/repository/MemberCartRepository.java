package org.zerock.j2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.j2.entity.MemberCart;

import java.util.List;

public interface MemberCartRepository extends JpaRepository<MemberCart, Long> {
    // @Query 어노테이션을 사용하여 쿼리 메서드를 직접 정의함
    @Query("select mc from MemberCart mc where mc.email = :email order by mc.cno asc")
        // :email은 쿼리 파라미터로 사용할 매개변수명이며, @Param 어노테이션으로 매개변수명과 쿼리 파라미터를 매핑함
    List<MemberCart> selectCart(@Param("email") String email); // email이라는 매개변수가 JPQL 쿼리에서 사용되는 파라미터임
    // List<MemberCart>: selectCart 메서드의 반환 타입은 List<MemberCart>으로, 쿼리 실행 결과를 MemberCart 엔티티의 리스트로 받아옵니다.
}

