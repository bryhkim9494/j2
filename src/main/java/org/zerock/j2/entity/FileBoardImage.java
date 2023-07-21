package org.zerock.j2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileBoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgno; // 파일 게시판 이미지의 고유 번호 (PK)
    private String uuid; // 파일 고유 식별자 (UUID)
    private String fname; // 파일 이름
    private int ord; // 이미지의 순서를 나타내는 변수

    public void changeOrd(int ord) { // 이미지의 순서를 변경하는 메서드
        this.ord = ord;
    }
}
