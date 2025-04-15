package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	Admin findByUsername(String username);

}
