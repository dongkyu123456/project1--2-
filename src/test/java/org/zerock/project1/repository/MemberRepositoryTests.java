package org.zerock.project1.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.project1.entity.Member;

@SpringBootTest
public class MemberRepositoryTests {

  @Autowired
  private MemberRepository repository;

  @Test
  public void insertMembers() {
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Member member = Member.builder()
        .email("user"+i+"@aaa.com")
        .password("1")
        .name(String.format("USER%d", i))
        .build();
      repository.save(member);
    });
  }
}