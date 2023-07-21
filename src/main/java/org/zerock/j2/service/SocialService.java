package org.zerock.j2.service;

import jakarta.transaction.Transactional;

@Transactional
public interface SocialService {

    // 카카오 로그인을 통해 얻은 인증 코드(authCode)를 이용하여 사용자의 이메일 주소를 가져오는 메소드
    // 반환값: 사용자의 이메일 주소 (문자열)
    String getKakaoEmail(String authCode);
}
