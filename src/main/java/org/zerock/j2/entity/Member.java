package org.zerock.j2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    @Id
    private String email; //아이디 역할
    private String pw;
    private String nickname;
    private boolean admin;

}
