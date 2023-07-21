package org.zerock.j2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.j2.entity.Product;
import org.zerock.j2.repository.search.ProductSearch;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    @EntityGraph(attributePaths = "images") // @EntityGraph 어노테이션은 JPA 엔티티 그래프를 정의하는데 사용됩니다.
    // attributePaths 속성에 "images"라고 지정하여 Product 엔티티를 조회할 때 images 속성의 정보를 함께 로딩합니다.

    // @Query 어노테이션을 사용하여 직접 JPQL 쿼리를 작성합니다.
    // :pno는 파라미터를 바인딩하는데 사용되며, 파라미터로 전달된 pno와 일치하는 Product 엔티티를 조회합니다.
    // delFlag 속성이 false인 Product 엔티티만 조회합니다. (물리적으로 삭제되지 않은 데이터만 조회)
    @Query("select p from Product p where  p.delFlag =false and p.pno = :pno")
    Product selectOne(@Param("pno") Long pno);
}
