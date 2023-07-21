package org.zerock.j2.repository.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.entity.Product;
import org.zerock.j2.entity.QProduct;
import org.zerock.j2.entity.QProductImage;
import org.zerock.j2.entity.QProductReview;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    // Product 엔티티와 연결된 ProductSearchImpl 생성
    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        // Querydsl에서 사용할 별칭(alias)으로 QProduct, QProductImage를 생성
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        // 상품과 상품 이미지를 left join으로 연결하여 쿼리 시작
        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.images, productImage);
        query.where(productImage.ord.eq(0));

        // 페이지 정보 설정
        int pageNum = pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1;
        Pageable pageable = PageRequest.of(pageNum, pageRequestDTO.getSize(), Sort.by("pno").descending());
        this.getQuerydsl().applyPagination(pageable, query);

        // 쿼리 결과를 DTO로 변환하여 조회
        JPQLQuery<ProductListDTO> dtoQuery = query.select(
                Projections.bean(ProductListDTO.class, product.pno, product.pname, product.price, productImage.fname));
        List<ProductListDTO> dtoList = dtoQuery.fetch();
        Long totalCount = dtoQuery.fetchCount();

        // 페이징 처리된 목록과 총 결과 수를 담은 PageResponseDTO 객체를 반환
        return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);
    }

    @Override
    public PageResponseDTO<ProductListDTO> listWithReview(PageRequestDTO pageRequestDTO) {

        // Querydsl에서 사용할 별칭(alias)으로 QProduct, QProductImage, QProductReview를 생성
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;
        QProductReview reivew = QProductReview.productReview;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.images, productImage);
        query.leftJoin(reivew).on(reivew.product.eq(product));
        query.where(productImage.ord.eq(0));
        query.where(product.delFlag.eq(Boolean.FALSE));

        int pageNum = pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() - 1;
        Pageable pageable = PageRequest.of(pageNum, pageRequestDTO.getSize(), Sort.by("pno").descending());
        this.getQuerydsl().applyPagination(pageable, query);

        query.groupBy(product);

        // 쿼리 결과를 DTO로 변환하여 조회
        JPQLQuery<ProductListDTO> dtoQuery = query.select( // Querydsl을 사용하여 ProductListDTO에 대한 조회 쿼리를 작성하는 부분입니다.

                // DTO를 생성하는 방법을 지정하는 메서드로, ProductListDTO 객체를 생성하고, 해당 객체의 필드들에 값을 매핑합니다. as() 메서드를 사용하여 각 값에 별칭을 부여합니다.
                Projections.bean(ProductListDTO.class,
                        product.pno,
                        product.pname,
                        product.price,
                        productImage.fname.min().as("fname"),
                        reivew.score.avg().as("reviewAvg"),
                        reivew.count().as("reviewCnt")));

        List<ProductListDTO> dtoList = dtoQuery.fetch(); // 쿼리 결과를 조회하여 ProductListDTO 타입의 리스트에 저장
        Long totalCount = dtoQuery.fetchCount(); // 쿼리 결과의 총 개수를 조회하여 totalCount 변수에 저장


        // 페이징 처리된 목록과 총 결과 수를 담은 PageResponseDTO 객체를 반환
        return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);
    }
}
