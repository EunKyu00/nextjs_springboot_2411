package com.rest.global.security;

import com.rest.domain.member.service.MemberService;
import com.rest.global.rp.Rq;
import com.rest.global.rsData.RsData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final Rq rq;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        if (request.getRequestURI().equals("/api/v1/members/login") || request.getRequestURI().equals("/api/v1/members/logout")){
            filterChain.doFilter(request,response);
            return;
        }
        String accessToken = rq.getCookie("accessToken");
        //accessToken 검증 or 리프레쉬토큰 발급

        if (!accessToken.isBlank()){
            if ( !memberService.validateToken(accessToken)){
                String refreshToken = rq.getCookie("refreshToken");
                RsData<String> rs = memberService.refreshAccessToken(refreshToken);

                rq.setCrossDomainCookie("accessToken", rs.getData());
            }

            // SecurityUser 가져오기
            SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);

            // 로그인 처리
            rq.setLogin(securityUser);
        }
        filterChain.doFilter(request,response);
    }
}
