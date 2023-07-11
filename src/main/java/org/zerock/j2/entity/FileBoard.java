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
    private Long bno;

    private String title;

    private String content;

    private String writer;

    @BatchSize(size = 20) // BatchSize는 20개로 일괄처리 
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY) //FetchType은 기본이 EAGER말고 LAZY이다.
    @JoinColumn(name = "board")
    @Builder.Default
    private List<FileBoardImage> images = new ArrayList<>();// one to many 연관관계

    public void addImage(FileBoardImage boardImage) {
        boardImage.changeOrd(images.size());
        images.add(boardImage);

    }
}
