package org.zerock.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.project1.entity.Board;
import org.zerock.project1.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
  @Modifying
  @Query("delete from Reply r where r.board.id=:id")
  void deleteByid(Long id);

  List<Reply> getRepliesByBoardOrderByid(Board board);


}