package com.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.demo.model.Member;
import com.demo.repository.MemberRepository;
import com.demo.utils.JwtTokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("登入成功但找不到帳號"));
        String roles = member.getAuthority(); // 確保你的 Member 有這個方法

        // 產生 JWT
        String token = jwtTokenProvider.generateToken(member.getEmail(),roles);

        // 回傳到前端（用 redirect 搭 token）
        response.sendRedirect("http://localhost:5173/oauth2/success?token=" + token);
    }
}