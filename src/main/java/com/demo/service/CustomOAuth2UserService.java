package com.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.demo.model.Member;
import com.demo.repository.MemberRepository;

@Component
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private MemberRepository memberRepository;

    
    //定義一個自訂的服務，處理從 Google 拿回來的用戶資料
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        //如果沒找到，表示第一次用 Google 登入，就自動幫他註冊進資料庫 ✅
        if (memberOptional.isEmpty()) {
            Member newMember = new Member();
            newMember.setEmail(email);
            newMember.setFullName(name);
//            newMember.setUsername("userTest");
            newMember.setAuthority("USER");
            newMember.setTotalMiles(0);
            newMember.setRemainingMiles(0);
//            newMember.setPicture(picture);
//            newMember.setProvider("GOOGLE");
            memberRepository.save(newMember);
        }
        //回傳用戶資訊給 Spring Security，表示登入成功
        return oAuth2User;
    }
}