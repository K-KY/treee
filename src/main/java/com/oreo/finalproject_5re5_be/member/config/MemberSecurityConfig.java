package com.oreo.finalproject_5re5_be.member.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MemberSecurityConfig {

    private final LoginAuthenticationSuccessHandler successHandler;

    public MemberSecurityConfig(LoginAuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    // SecurityFilterChain 설정 빈 등록, 추후에 적용 예정(다른 파트 작업 완료후 인증/인가 처리 적용예정)
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // csrf 비활성화
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/", "/api/member/register") // 회원가입 페이지와 홈 페이지는 인증/인가 없이 접근 가능
//                        .permitAll()
//                        .requestMatchers("/audio/**", "/project/**", "/languagecode/**",
//                                "/voice/**", "/style", "/vc/**", "/concat") // 그외의 페이지는 인증/인가가 필요함
//                        .authenticated()
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/api/member/login") // 로그인 페이지 경로 설정
//                        .successHandler(successHandler) // 로그인 성공시 처리되는 핸들러 설정
//                        .failureUrl("/api/member/login") // 로그인 실패시 로그인 페이지로 이동
//                ).logout(logout -> logout
//                        .logoutUrl("/api/member/logout") // 로그아웃 경로 설정
//                        .invalidateHttpSession(true) // 로그아웃시 세션 무효화 설정
//                        .logoutSuccessUrl("/") // 로그아웃 성공시 이동할 페이지 설정
//                );
//
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // csrf 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/member/**", "/api/member/**",
                                         "/audio/**", "/project/**", "/languagecode/**",
                                         "/voice/**", "/style/**", "/vc/**", "/concat/**",
                                         "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html") // 다른 파트 개발 작업 진행으로 모든 페이지는 인증/인가 처리 x
                        .permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/api/member/login") // 로그인 페이지 경로 설정 - 이 부분 프론트엔드 페이지 요청 경로로 변경하기
                        .successHandler(successHandler) // 로그인 성공시 처리되는 핸들러 설정
                        .failureUrl("/api/member/login") // 로그인 실패시 로그인 페이지로 이동 - 이 부분 프론트엔드 페이지 요청 경로로 변경하기
                ).logout(logout -> logout
                        .logoutUrl("/api/member/logout") // 로그아웃 경로 설정
                        .invalidateHttpSession(true) // 로그아웃시 세션 무효화 설정
                        .logoutSuccessUrl("/") // 로그아웃 성공시 이동할 페이지 설정
                );


        return http.build();
    }
}
