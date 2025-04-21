package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Security.annotation.RequireJwt;
import com.demo.model.Member;
import com.demo.service.MemberService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
//	查詢單筆會員(測試)
	@GetMapping("/GetMember/{id}")
	public Member GetMember(@PathVariable Integer id) {
		return memberService.getOne(id);
	}

//	查詢所有會員
	@GetMapping("/GetAllMember")
	public List<Member> GetAllMembers(){
		return memberService.getAll();
	}
//	新增會員
	@PostMapping("/InsertMember")
	public Member insertMember(@RequestBody Member member) {
		return memberService.insertMember(member);
	}
//	透過id更新會員資訊
	@PutMapping("/UpdateMember")
	public Member updateMemberById(@RequestBody Member member) {
		return memberService.updateMemberById(member);
	}
	
	
//  透過id刪除會員
	@DeleteMapping("/DeleteMember/{id}")
	public String deleteMemberById(@PathVariable Integer id) {
		boolean result = memberService.deleteMemberById(id);
		if (result) {
			return "成功刪除";
		}
		return "查無此會員";
	}
//  更新里程數(累積里程)
	
	@RequireJwt
	@PutMapping("/IncreaseMiles/{id}/{increaseMiles}")
	public Member increaseMilesById(@PathVariable Integer id,
									@PathVariable Integer increaseMiles) {
		
		return memberService.increaseMilesById(id, increaseMiles);
		
	}
	
//  更新里程數(花費旅程 扣款)
	@PutMapping("/DecreaseMiles/{id}/{decreaseMiles}")
	public Member decreaseMilesById(@PathVariable Integer id,
									@PathVariable Integer decreaseMiles) {
		
		return memberService.decreaseMilesById(id, decreaseMiles);
		
	}
	
//透過username找尋
	@GetMapping("/GetMemberByUsername/{username}")
	public Member GetMemberByUsername(@PathVariable String username) {
		return memberService.getOneByUsername(username);
	}
	
	
	
	//透過email找尋
		@GetMapping("/GetMemberByEmail/{email}")
		public Member GetMemberByEmail(@PathVariable String email) {
			return memberService.getOneByEmail(email);
		}	
	
	

}
