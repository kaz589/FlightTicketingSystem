package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.model.Member;
import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Integer> {
	
	Optional<Member> findByUsername(String username);
	Optional<Member> findByEmail(String email);
	
	@Query("Select m From Member m Where m.authority LIKE CONCAT('%', :role, '%')")
	List<Member> findAllByAuthorityContaining(@Param("role") String role);
}
