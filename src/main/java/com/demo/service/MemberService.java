package com.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.demo.controller.PasswordHashing;
import com.demo.model.Member;
import com.demo.model.PasswordResetToken;
import com.demo.repository.MemberRepository;
import com.demo.repository.PasswordResetTokenRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Autowired
	private EmailService emailService;
	
	
	 // 引入 BCryptPasswordEncoder 用於密碼加密
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Member getOne(Integer id) {
		
		Optional<Member> op = memberRepository.findById(id);
		if (op.isPresent()) {
			Member member = op.get();
//			System.out.println("找到會員：");
//	        System.out.println("會員ID: " + member.getMemberId());
//	        System.out.println("電子郵件: " + member);
//	        System.out.println("會員名稱: " + member);
			
			return member;
		}
//		System.out.println("找不到");
		return null;
	}
	
	public List<Member> getAll() {
		return memberRepository.findAll();		
	}
	
	
	//找所有身分是ADMIN的Member
	public List<Member> getAllWhereAuthorityIsAdmin() {
		return memberRepository.findAllByAuthorityContaining("ADMIN");
	}
	
	
	
	
	
	
	//判斷username是否存在
		public boolean usernameExist(String username) {
			
			
			//透過username查admin
			Optional<Member> op = memberRepository.findByUsername(username);
			if (op.isEmpty()) {
	            return false;
	       }
		 return true;
		}
	
	
	//新增會員
	public Member insertMember(Member member) {
		
		
		// 要確認username是否重複
		// 先透過username查有沒有密碼
		boolean result = usernameExist(member.getUsername());
		if (result) {
			//username存在，不能新增
			return null;
			
		}		
		//密碼需要加密
		String password = member.getPassword();
		
		
		String password_Hashing = passwordEncoder.encode(password);  // 使用 BCrypt 進行加密
		member.setPassword(password_Hashing);
		member.setTotalMiles(0); //初始值0
		member.setRemainingMiles(0); //初始值0
		member.setMembershipLevel("普通會員");
		
		memberRepository.save(member);
		return member;
	}
	
	
	//新增管理員
		public Member insertMemberDefaultAdmin(Member member) {
			
			
			// 要確認username是否重複
			// 先透過username查有沒有密碼
			boolean result = usernameExist(member.getUsername());
			if (result) {
				//username存在，不能新增
				return null;
				
			}		
			//密碼需要加密
			String password = member.getPassword();
			
			
			String password_Hashing = passwordEncoder.encode(password);  // 使用 BCrypt 進行加密
			member.setPassword(password_Hashing);
			member.setTotalMiles(0); //初始值0
			member.setRemainingMiles(0); //初始值0
			member.setAuthority("ADMIN");//預設為ADMIN
			member.setMembershipLevel("普通會員");
			
			memberRepository.save(member);
			return member;
		}
	
	
	
	
	
	
	
// 先找再更改
	public Member updateMemberById(Member member) {
		Optional<Member> op = memberRepository.findById(member.getMemberId());
		
		 if (op.isEmpty()) {
		        throw new NoSuchElementException("找不到該會員 ID：" + member.getMemberId());
		    }else {
		    	
		    	//如果找得到
				Member dbMember = op.get();
				dbMember.setFullName(member.getFullName());
				dbMember.setUsername(member.getUsername());
				
				//密碼需要加密
				String newPassword = member.getPassword();
				if (newPassword != null && !newPassword.equals(dbMember.getPassword())) {
				    // 只有當新密碼和原本的不同時，才進行加密與更新
				    String encodedPassword = passwordEncoder.encode(newPassword);
				    dbMember.setPassword(encodedPassword);
				}			
				dbMember.setEmail(member.getEmail());
				dbMember.setTotalMiles(member.getTotalMiles());
				dbMember.setRemainingMiles(member.getRemainingMiles());
				dbMember.setPhoneNumber(member.getPhoneNumber());
				dbMember.setRegistrationDate(member.getRegistrationDate());
				dbMember.setEmailVerified(member.isEmailVerified());
				dbMember.setPhoneVerified(member.isPhoneVerified());
				dbMember.setPicture(member.getPicture());
				dbMember.setAuthorityDetail(member.getAuthorityDetail());
				
				// 抽出方法處理會員等級(升等邏輯)
				if (member.getTotalMiles() != null) {
					
					dbMember.setMembershipLevel(determineMembershipLevel(member.getTotalMiles()));
				}
							
				memberRepository.save(dbMember);
				return dbMember;
		    	
		    }		
	}	
	
//只能更新權限
	public boolean updateJustAuthorityDetail(Integer id,String authorityDetail) {
		Optional<Member> op = memberRepository.findById(id);
		
		//如果找不到
		if (op.isEmpty()) {
	        return false;
	    }else {
	    	
	    	//如果找得到
			Member dbMember = op.get();
			dbMember.setAuthorityDetail(authorityDetail);
			
			memberRepository.save(dbMember);
			return true;
	    }
	}
	
//只更新角色
	public boolean updateJustAuthority(Integer id, String authority) {
		Optional<Member> op = memberRepository.findById(id);
		//如果找不到
				if (op.isEmpty()) {
			        return false;
			    }else {
			    	
			    	//如果找得到
					Member dbMember = op.get();
					dbMember.setAuthority(authority);
					
					memberRepository.save(dbMember);
					return true;
			    }
	}
	
	
	
	
	
	
	
	
// 刪除
	public boolean deleteMemberById(Integer id) {
		
		Optional<Member> op = memberRepository.findById(id);
		
		if (op.isPresent()) {
			memberRepository.deleteById(id);
			return true;
		}else {
			return false;
		}
		
		
		
	}
// 透過id找會員，並新增某會員里程數
	public Member increaseMilesById(Integer id, Integer increaseMiles) {
		Optional<Member> op = memberRepository.findById(id);
		
		if (op.isPresent()) {
			Member member = op.get();
			Integer resultRemainingMiles = member.getRemainingMiles() + increaseMiles;
			Integer resultTotalMiles = member.getTotalMiles() + increaseMiles;
			member.setTotalMiles(resultTotalMiles);
			member.setRemainingMiles(resultRemainingMiles);
			memberRepository.save(member);
			return member;
		}
		return null;		
	}

	// 透過id找會員，並減少某會員里程數
		public Member decreaseMilesById(Integer id, Integer decreaseMiles) {
			Optional<Member> op = memberRepository.findById(id);
			
			if (op.isPresent()) {
				Member member = op.get();
				Integer resultRemainingMiles = member.getRemainingMiles() - decreaseMiles;
				member.setRemainingMiles(resultRemainingMiles);
				memberRepository.save(member);
				return member;
			}
			return null;		
		}
		
	// 透過username找會員
		public Member getOneByUsername(String username) {
			
			Optional<Member> op = memberRepository.findByUsername(username);
			if (op.isEmpty()) {
	            throw new UsernameNotFoundException("使用者不存在: " + username);
	       }
		 return op.get();
		}
		// 透過email找會員
		public Member getOneByEmail(String email) {
			
			  Optional<Member> op = memberRepository.findByEmail(email);
			 if (op.isEmpty()) {
		            throw new UsernameNotFoundException("使用者不存在: " + email);
		       }
			 return op.get();
		}
		
		
		
		//密碼變更
		public void createPasswordResetTokenForMember(Member member) {
		    String token = UUID.randomUUID().toString();
		    
		    PasswordResetToken resetToken = new PasswordResetToken();
		    resetToken.setToken(token);	  //設定token
		    resetToken.setMember(member); // 
		    resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));

		    passwordResetTokenRepository.save(resetToken);

		    // 👉 你可以在這裡加寄信邏輯，把 token 組成連結寄出去
		    emailService.sendResetPasswordEmail(member.getEmail(), token);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		private String determineMembershipLevel(int miles) {
		    if (miles >= 100000) return "鑽石會員";
		    if (miles >= 50000) return "金卡會員";
		    if (miles >= 25000) return "銀卡會員";
		    return "普通會員";
		}
		
		
	
	
}
