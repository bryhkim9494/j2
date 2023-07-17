package org.zerock.j2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCartDTO {
    private Long cno; //삭제하기 위해 필요함

    private String email;
    private Long pno;
}
