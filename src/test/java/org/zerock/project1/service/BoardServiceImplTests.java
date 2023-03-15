package org.zerock.project1.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.project1.dto.BoardDTO;
import org.zerock.project1.dto.PageRequestDTO;
import org.zerock.project1.dto.PageResultDTO;

@SpringBootTest
class BoardServiceImplTests {

  @Autowired
  private BoardService service;

  @Test
  void register() {
    BoardDTO dto = BoardDTO.builder()
        .title("Test...")
        .content("Test...")
        .writerEmail("user55@aaa.com")
        .build();
    Long id = service.register(dto);
  }

  @Test
  public void testList() {
    PageRequestDTO pageDTO = new PageRequestDTO();
    PageResultDTO<BoardDTO, Object[]> result = service.getList(pageDTO);
    for (BoardDTO dto : result.getDtoList()) {
      System.out.println(dto);
    }
  }
  @Test
  public void testGet(){
    Long id = 10L;
    BoardDTO dto = service.get(id);
    System.out.println(dto);
  }

  @Test
  public void testDelete() {
    service.removeWithReplies(1L);
  }

  @Test
  public void testModify() {
    BoardDTO dto = BoardDTO.builder().id(2L)
        .title("Update title").content("Update Content").build();
    service.modify(dto);
  }
}