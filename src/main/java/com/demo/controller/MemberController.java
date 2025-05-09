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
import com.demo.dto.MemberAuthorityDto;
import com.demo.model.Member;
import com.demo.model.DTO.MembershipCountDTO;
import com.demo.model.DTO.ProviderCountDTO;
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

//  透過姓名查詢會員
	@GetMapping("GetAllMemberByFullname/{name}")
	public List<Member> GetAllMemberByFullname(@PathVariable String name){
		return memberService.getAllByFullName(name);
	}
	
	
//  查詢所有管理員
	@GetMapping("/GetAllAdminList")
	public List<Member> GetAllAdminList(){
		return memberService.getAllWhereAuthorityIsAdmin();
	}
	
	
	
	
	
//	新增會員
	@PostMapping("/InsertMember")
	public Member insertMember(@RequestBody Member member) {
		return memberService.insertMember(member);
	}
	
//	新增會員(預設為管理員)
	@PostMapping("/InsertMemberDefaultAdmin")
	public Member insertMemberDefaultAdmin(@RequestBody Member member) {
		return memberService.insertMemberDefaultAdmin(member);
	}
	
	
	
	
	
	
	
//	透過id更新會員資訊
	@PutMapping("/UpdateMember")
	public Member updateMemberById(@RequestBody Member member) {
		return memberService.updateMemberById(member);
	}
	
// 透過id更新權限(僅權限)	
	@PutMapping("/UpdateMemberAuthorityDetail")
	public boolean updateMemberAuthorityDetailById(@RequestBody MemberAuthorityDto memberDto) {
		return memberService.updateJustAuthorityDetail(memberDto.getId(),memberDto.getAuthorityDetail());
	}
// 透過id更新權限(僅角色)	
	@PutMapping("/UpdateMemberAuthority")
	public boolean updateMemberAuthorityById(@RequestBody MemberAuthorityDto memberDto) {
		return memberService.updateJustAuthority(memberDto.getId(),memberDto.getAuthority());
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
	@PutMapping("/DecreaseMiles/{memberId}/{decreaseMiles}")
	public Member decreaseMilesById(@PathVariable Integer memberId,
									@PathVariable Integer decreaseMiles) {
		
		return memberService.decreaseMilesById(memberId, decreaseMiles);
		
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
		
	//找所有user的會員等級各有多少人
	@GetMapping("/CountMembershipLevel")
	public List<MembershipCountDTO> CountMembershipLevel(){
		return memberService.countMemberShip();
	}
	
	//找不同登入方式的人各有多少
	@GetMapping("/CountProvider")
	public List<ProviderCountDTO> CountProvider(){
		return memberService.countProvider();
	}
	

}
