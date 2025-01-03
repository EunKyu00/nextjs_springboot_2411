package com.rest.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/api/**")
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/api/*/articles").permitAll()
                                .requestMatchers("/api/*/articles/*").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/*/members/login").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/*/members/logout").permitAll()
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())//csrf 토큰 끄기
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // cors 설정 추가
                .httpBasic(httpBasic -> httpBasic.disable()) //httpBasic 로그인 방식 끝기
                .formLogin(formLogin -> formLogin.disable())// 폼 로그인 방식 끄기
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS))//세션 끄기
                .addFilterBefore(
                        jwtAuthorizationFilter, //엑세스 토큰을 이용한 로그인 처리
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000"); // 허용할 출처 추가
        corsConfiguration.addAllowedOrigin("http://cdpn.io"); //추가 허용 출처
        corsConfiguration.addAllowedMethod("*"); //모든 HTTP 메서드 허용
        corsConfiguration.addAllowedHeader("*"); // 모든 요청 헤더 허용
        corsConfiguration.setAllowCredentials(true); //쿠키 및 인증 정보 포함 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration); //모든 경로에 CORS 정책 적용
        return source;
    }

}