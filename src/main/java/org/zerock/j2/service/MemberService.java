package org.zerock.j2.service;

import jakarta.transaction.Transactional;
import org.zerock.j2.dto.MemberDTO;

@Transactional
public interface MemberService {
    MemberDTO login(String email, String pw); // 회원 로그인을 처리하는 메서드

    MemberDTO getMemberWithEmail(String email); // 이메일 주소로 회원 정보를 조회하는 메서드


}
