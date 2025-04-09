package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

}
