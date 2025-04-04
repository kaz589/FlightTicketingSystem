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

}
