package org.zerock.project1.repository;

import org.zerock.project1.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String>{
  
}