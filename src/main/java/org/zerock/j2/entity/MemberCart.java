package org.zerock.j2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_cart", indexes = @Index(columnList = "email,cno"))
public class MemberCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno; //삭제하기 위해 필요함
    private String email;
    private Long pno;

    // 시간이 추가되어야함

}
