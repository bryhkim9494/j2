package org.zerock.j2.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString(exclude = "images")
@AllArgsConstructor
@NoArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno; // 상품의 고유 번호 (PK)

    private String pname;// 데이터베이스에 name값이 예약어일수있어서 name을 못씀
    private String pdesc; // 상품 설명
    private String writer; // 작성자 (상품 등록자)
    private int price; // 상품 가격
    private boolean delFlag; // 삭제 여부를 나타내는 변수


    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>(); // 상품의 이미지 목록을 저장하는 리스트

    public void addImage(String name) { // 상품 이미지를 추가하는 메서드
        ProductImage pImage = ProductImage.builder().fname(name).ord(images.size()).build();
        images.add(pImage);

    }

    public void clearImages() { // 상품의 이미지 목록을 모두 삭제하는 메서드
        images.clear();
    }

    public void changePrice(int price) { // 상품의 가격을 변경하는 메서드
        this.price = price;
    }

    public void changePname(String pname) {// 상품명을 변경하는 메서드
        this.pname = pname;

    }

    public void changePdesc(String pdesc) { // 상품 설명을 변경하는 메서드
        this.pdesc = pdesc;

    }

    public void changeDel(boolean delFlag) { // 상품의 삭제 여부를 변경하는 메서드
        this.delFlag = delFlag;

    }
}
