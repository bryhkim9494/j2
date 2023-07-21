package org.zerock.j2.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.j2.controller.interceptor.JWTInterceptor;

@Configuration // Spring Bean Configuration 클래스임을 나타냄
@EnableWebMvc // Spring Web MVC 기능을 활성화시키는 어노테이션
@Log4j2 // 로깅을 위한 Lombok 어노테이션
@RequiredArgsConstructor // 생성자 인젝션을 위한 Lombok 어노테이션
public class CustomServletConfig implements WebMvcConfigurer {

    private final JWTInterceptor jwtInterceptor; // JWTInterceptor 의존성 주입

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // InterceptorRegistry에 JWTInterceptor를 등록하고 경로를 지정함
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**") // "/api/**"로 시작하는 모든 경로에 인터셉터를 적용
                .excludePathPatterns("/api/member/login", "/api/member/refresh"); // "/api/member/login"과 "/api/member/refresh" 경로는 제외
    }
}