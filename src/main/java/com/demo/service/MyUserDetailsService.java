package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.model.Member;
import com.demo.model.PO.MyUser;
import com.demo.repository.MemberRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberRepository memberRepo;
	
	
	@Override
    public MyUser loadUserByUsername(String username) 
                  throws UsernameNotFoundException {
		
		// 先試著當 username 查
          Optional<Member> member = memberRepo.findByUsername(username);
        //再用Email查
        if (member.isEmpty()) {
//            throw new UsernameNotFoundException("使用者不存在: " + username);
        	member = memberRepo.findByEmail(username);
        }
        
        
        // 如果還是找不到，拋出 UsernameNotFoundException
        if (member.isEmpty()) {
            throw new UsernameNotFoundException("使用者不存在: " + username);
        }
        
     // 如果找到，則返回 MyUser 物件
        return new MyUser(member.get());
    }
	
	// 根據 Member 的權限來設置角色（簡化示例）
    private List<GrantedAuthority> getAuthorities(Member member) {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
	
}
