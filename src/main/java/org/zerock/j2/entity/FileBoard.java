package org.zerock.j2.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "images")
public class FileBoard {// many to one은 db중심 one to many는 아님
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno; // 파일 게시판의 고유 번호 (PK)
    private String title; // 파일 게시판 제목
    private String content; // 파일 게시판 내용
    private String writer; // 파일 게시판 작성자

    @BatchSize(size = 20) // images 필드의 데이터를 20개씩 일괄 처리하는 설정
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    // OneToMany 관계 설정: 파일 게시판과 파일 게시판 이미지의 일대다 관계를 설정합니다.
    // FetchType은 기본이 EAGER말고 LAZY이다.
    @JoinColumn(name = "board") // 연관된 이미지들을 저장할 외래 키 컬럼 이름을 지정합니다.
    @Builder.Default
    private List<FileBoardImage> images = new ArrayList<>();// 파일 게시판과 연관된 이미지들을 담을 리스트입니다.
    // 실무에서는 many to one으로 해결하려고함

    public void addImage(FileBoardImage boardImage) { // 이미지를 추가하는 메서드
        boardImage.changeOrd(images.size()); // 이미지의 순서를 현재 리스트의 크기로 지정합니다.
        images.add(boardImage); // 이미지를 리스트에 추가합니다.
    }

    public void clearImages() { // 이미지 리스트를 비웁니다.
        images.clear();
    }
}
