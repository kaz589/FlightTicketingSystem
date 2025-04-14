package com.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.controller.AdminController;
import com.demo.model.Admin;
import com.demo.repository.AdminRepository;

@Service
public class AdminService {


	
	@Autowired
	private AdminRepository adminRepository;


	
	
	public Admin findAdminById(Integer id) {
		Optional<Admin> op = adminRepository.findById(id);
		if (op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	
	//判斷username是否存在
	public boolean usernameExist(String username) {
		
		//透過username查admin
		Admin admin = adminRepository.findByUsername(username);
		if (admin != null) {
			return true;
		}
		return false;
	}
	
	
	
	//	新增管理員
	public Admin insertAdmin(Admin admin) {
		// 要確認username是否重複
		// 先透過username查有沒有密碼
		boolean result = usernameExist(admin.getUsername());
		
		if (result) {
			//username存在，不能新增
			return null;
			
		}
		//沒有使用者(才能新增)
		adminRepository.save(admin);
		return admin;
		
	}
// 透過username找password
	public boolean getPassword(Admin admin) {
		 Admin admin_db = adminRepository.findByUsername(admin.getUsername());
		 System.out.println("查到的" + admin_db.getPassword());
		 System.out.println("使用者給的" + admin.getPassword());
		 
		 if(admin_db.getPassword().equals(admin.getPassword())) {
			 
			 return true;
		 }
		 return false;
	
	}


}
