//package com.demo.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.demo.model.Member;
//import com.demo.model.PO.MyUser;
//import com.demo.repository.MemberRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final MemberRepository memberRepository;
//
//    @Autowired
//    public CustomUserDetailsService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    
//    
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member member = memberRepository.findByUsername(username);
//
//        if (member == null) {
//            throw new UsernameNotFoundException("使用者不存在: " + username);
//        }
//
//        return new MyUser(member); // ✅ 使用你自己寫的 MyUser，這樣 member 不會是 null
//    }
//
//    // 根據 Member 的權限來設置角色（簡化示例）
//    private List<GrantedAuthority> getAuthorities(Member member) {
//        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
//    }
//}
