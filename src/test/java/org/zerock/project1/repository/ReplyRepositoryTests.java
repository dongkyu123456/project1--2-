package org.zerock.project1.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.project1.entity.Board;
import org.zerock.project1.entity.Reply;

@SpringBootTest
public class ReplyRepositoryTests {

  @Autowired
  ReplyRepository repository;

  @Test
  public void insertReply() {
    IntStream.rangeClosed(1, 300).forEach(i -> {
      long id = (long) (Math.random() * 100) + 1;
      Board board = Board.builder().id(id).build();
      Reply reply = Reply.builder()
          .text("Reply....." + i)
          .board(board)
          .replyer("guest")
          .build();
      repository.save(reply);
    });
  }

  @Test
  public void readReply() {
    Optional<Reply> result = repository.findById(100L);
    Reply reply = result.get();
    System.out.println(reply);
    System.out.println(reply.getBoard());
  }

  @Test
  public void testListByBoard(){
    List<Reply> replyList = repository.getRepliesByBoardOrderByid(
        Board.builder().id(97L).build());
    replyList.forEach(r -> System.out.println(r));
  }
}