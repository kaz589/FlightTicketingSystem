package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Member;
import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Integer> {
	
	Optional<Member> findByUsername(String username);
	Optional<Member> findByEmail(String email);
}
