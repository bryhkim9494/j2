package org.zerock.j2.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;

@Service
@Log4j2
public class SocialServiceImpl implements SocialService {

    // application.properties 파일에서 값을 가져와서 설정하는 변수들
    @Value("${org.zerock.kakao.token_url}")
    private String tokenURL;
    @Value("${org.zerock.kakao.rest_key}")
    private String clientId;
    @Value("${org.zerock.kakao.redirect_uri}")
    private String redirectURI;
    @Value("${org.zerock.kakao.get_user}")
    private String getUser;

    // 카카오 로그인으로 받은 authCode를 이용하여 사용자의 이메일을 가져오는 메서드
    @Override
    public String getKakaoEmail(String authCode) {
        log.info("==========================");
        log.info("authCode:" + authCode);
        log.info(tokenURL);
        log.info(clientId);
        log.info(redirectURI);
        log.info(getUser);

        String accessToken = getAccessToken(authCode);
        String email = getEmailFromAccessToken(accessToken);
        return email;
    }

    // Access Token을 얻기 위한 메서드
    private String getAccessToken(String authCode) {
        String accessToken = null;
        RestTemplate restTemplate = new RestTemplate();

        // 카카오 토큰 요청에 필요한 HTTP 요청 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 카카오에게 Access Token을 요청하는 URI 생성
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(tokenURL).queryParam("grant_type", "authorization_code").queryParam("client_id", clientId).queryParam("redirect_uri", redirectURI).queryParam("code", authCode).build(true);

        // 생성한 URI를 사용하여 카카오에게 Access Token을 요청하고, 응답을 받아옴
        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(
                        uriComponents.toString(), HttpMethod.POST, entity, LinkedHashMap.class);

        log.info("-----------------------------------");
        log.info(response);
        LinkedHashMap<String, String> bodyMap = response.getBody();
        accessToken = bodyMap.get("access_token");
        log.info("Access Token: " + accessToken);

        return accessToken;
    }

    // Access Token을 이용하여 사용자의 이메일을 가져오는 메서드
    private String getEmailFromAccessToken(String accessToken) {

        if (accessToken == null) {
            throw new RuntimeException("Access Token is null");
        }
        RestTemplate restTemplate = new RestTemplate();

        // 카카오 사용자 정보를 요청할 때 사용하는 HTTP 요청 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 카카오 사용자 정보를 가져오기 위한 URI 생성
        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(getUser).build();

        // 생성한 URI를 사용하여 카카오에게 사용자 정보를 요청하고, 응답을 받아옴
        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(
                        uriBuilder.toString(),
                        HttpMethod.GET,
                        entity,
                        LinkedHashMap.class);

        log.info(response);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        log.info("------------------------------------");
        log.info(bodyMap);

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");

        log.info("kakaoAccount: " + kakaoAccount);

        return kakaoAccount.get("email");

    }
}
