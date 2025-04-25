package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Member;
import com.demo.model.PasswordResetToken;
import java.util.List;
import java.util.Optional;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer>{
	
	
	PasswordResetToken findByToken(String token);
}
