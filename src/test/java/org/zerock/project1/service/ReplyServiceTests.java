package org.zerock.project1.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.project1.dto.ReplyDTO;

import java.util.List;

@SpringBootTest
class ReplyServiceTests {

  @Autowired
  ReplyService service;

  @Test
  void register() {
  }

  @Test
  void getList() {
    Long id = 100L;
    List<ReplyDTO> replyDTOList = service.getList(id);
    replyDTOList.forEach(dto -> System.out.println(dto));
  }

  @Test
  void modify() {
  }

  @Test
  void remove() {
  }
}