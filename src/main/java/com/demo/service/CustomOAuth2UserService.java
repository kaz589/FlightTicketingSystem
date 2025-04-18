package com.demo.service;


import java.util.Map;
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

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google / facebook
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = null;
        String name = null;
        String picture = null;

        //判斷是哪裡的資料
        if ("google".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            picture = (String) attributes.get("picture");
        } else if ("facebook".equals(registrationId)) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            // Facebook 的 picture 是 nested 的，要特別解析
            Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
            if (pictureObj != null && pictureObj.containsKey("data")) {
                Map<String, Object> data = (Map<String, Object>) pictureObj.get("data");
                picture = (String) data.get("url");
            }
        }

        // ⚠️ 檢查 email，不然不能繼續（部分用戶可能隱藏 email）
        if (email == null) {
            throw new OAuth2AuthenticationException("Email not found from OAuth2 provider: " + registrationId);
        }

        // 新用戶註冊
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isEmpty()) {
            Member newMember = new Member();
            newMember.setEmail(email);
            newMember.setFullName(name);
            newMember.setAuthority("USER");
            newMember.setTotalMiles(0);
            newMember.setRemainingMiles(0);
//            newMember.setPicture(picture);
//            newMember.setProvider(registrationId.toUpperCase()); // GOOGLE / FACEBOOK
            memberRepository.save(newMember);
        }

        return oAuth2User;
    }
}
