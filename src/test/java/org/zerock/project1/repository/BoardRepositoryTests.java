package org.zerock.project1.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.project1.entity.Board;
import org.zerock.project1.entity.Member;

@SpringBootTest
public class BoardRepositoryTests {
  @Autowired
  private BoardRepository repository;

  @Test
  public void insertBoard(){
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Member member = Member.builder().email(String.format("user%d@aaa.com",i)).build();
      Board board = Board.builder()
        .title("Title..." + i)
        .content("Content" + i)
        .writer(member)
        .build();
      repository.save(board);
    });
  }
  @Test
  @Transactional
  public void testRead1(){
    Optional<Board> result = repository.findById(100L);
    Board board = result.get();
    System.out.println(board);
    System.out.println(board.getWriter());
  }

  @Test
  public void testReadWithWriter(){
    Object result = repository.getBoardwithWrtiter(100L);
    Object[] arr = (Object[]) result;
    System.out.println("------------------------------");
    System.out.println(Arrays.toString(arr));
  }
  @Test
  public void testGetBoardWithReply(){
    List<Object[]> result = repository.getBoardWithReply(100L);
    for (Object[] arr : result) {
      System.out.println(Arrays.toString(arr));
    }
  }

  @Test
  public void testWithReplyCount() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
    Page<Object[]> result = repository.getBoardWithReplyCount(pageable);
    result.get().forEach(new Consumer<Object[]>() {
      @Override
      public void accept(Object[] row) {
        Object[] arr = (Object[]) row;
        System.out.println(Arrays.toString(arr));
      }
    });
  }

  @Test
  public void testRead3() {
    Object result = repository.getBoardByid(100L);
    Object[] arr = (Object[]) result;
    System.out.println(Arrays.toString(arr));
  }

  @Test
  public void testSearch1() {
    repository.search1();
  }

  @Test
  public void testSearchPage() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending()
        .and(Sort.by("title").ascending()));
    Page<Object[]> result = repository.searchPage("t", "1", pageable);
  }
}