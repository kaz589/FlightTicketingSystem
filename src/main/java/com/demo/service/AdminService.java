package com.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
//	新增管理員
	public Admin insertAdmin(Admin admin) {
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
