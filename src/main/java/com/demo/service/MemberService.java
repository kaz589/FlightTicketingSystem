package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.controller.PasswordHashing;
import com.demo.model.Member;
import com.demo.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;

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
	
	public Member insertMember(Member member) {
		
		//密碼需要加密
		String password = member.getPassword();
		String password_Hashing = PasswordHashing.hashPassword(password);
		member.setPassword(password_Hashing);
		
		memberRepository.save(member);
		return member;
	}
// 先找再更改
	public Member updateMemberById(Member member) {
		Optional<Member> op = memberRepository.findById(member.getMemberId());
		if (op.isPresent()) {
			Member dbMember = op.get();
			dbMember.setFullName(member.getFullName());
			dbMember.setUsername(member.getUsername());
			
			//密碼需要加密
			String password = member.getPassword();
			String password_Hashing = PasswordHashing.hashPassword(password);
			dbMember.setPassword(password_Hashing);
			
			dbMember.setEmail(member.getEmail());
			dbMember.setTotalMiles(member.getTotalMiles());
			dbMember.setRemainingMiles(member.getRemainingMiles());
			dbMember.setPhoneNumber(member.getPhoneNumber());
			dbMember.setRegistrationDate(member.getRegistrationDate());
			dbMember.setEmailVerified(member.isEmailVerified());
			dbMember.setPhoneVerified(member.isPhoneVerified());
			dbMember.setMembershipLevel(member.getMembershipLevel());
			memberRepository.save(dbMember);
			return dbMember;
		}else {
			//密碼需要加密
			String password = member.getPassword();
			String password_Hashing = PasswordHashing.hashPassword(password);
			member.setPassword(password_Hashing);
			
			memberRepository.save(member);
			return member;
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
//		public Member decreaseMilesById(Integer id, Integer decreaseMiles) {
//			Optional<Member> op = memberRepository.findById(id);
//			
//			if (op.isPresent()) {
//				Member member = op.get();
//				Integer resultRemainingMiles = member.getRemainingMiles() - decreaseMiles;
//				member.setRemainingMiles(resultRemainingMiles);
//				memberRepository.save(member);
//				return member;
//			}
//			return null;		
//		}
	// 透過memberId找會員，並減少某會員里程數
			public Member decreaseMilesById(Integer memberId, Integer decreaseMiles) {
				Optional<Member> op = memberRepository.findById(memberId);
				
				if (op.isEmpty()) {
					new RuntimeException("找不到 ID 為 " + memberId + " 的會員");
				}	
				Member member = op.get();
				if (decreaseMiles <= 0) {
			            throw new IllegalArgumentException("扣除的里程數必須大於 0。");
			        }
				// 檢查里程足夠
				int available = member.getRemainingMiles();
				if (available < decreaseMiles) {
					throw new IllegalArgumentException(String.format("里程不足：共需要 %d里程，目前帳號剩餘 %d里程", decreaseMiles, available));
				}
					member.setRemainingMiles(available-decreaseMiles);
					memberRepository.save(member);
					return member;
						
			}
			

		
		
		
	
	
}
