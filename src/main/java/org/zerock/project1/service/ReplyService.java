package org.zerock.project1.service;

import org.zerock.project1.dto.ReplyDTO;
import org.zerock.project1.entity.Board;
import org.zerock.project1.entity.Reply;

import java.util.List;

public interface ReplyService {
  Long register(ReplyDTO dto);

  List<ReplyDTO> getList(Long id);

  void modify(ReplyDTO dto);

  void remove(Long id);

  default Reply dtoToEntity(ReplyDTO dto) {
    Board board = Board.builder().id(dto.getid()).build();
    Reply reply = Reply.builder()
        .id(dto.getid())
        .text(dto.getText())
        .replyer(dto.getReplyer())
        .board(board)
        .build();
    return reply;
  }

  default ReplyDTO entityToDTO(Reply reply) {
    ReplyDTO dto = ReplyDTO.builder()
        .id(reply.getid())
        .text(reply.getText())
        .replyer(reply.getReplyer())
        .created_at(reply.getcreated_at())
        .modDate(reply.getModDate())
        .build();
    return dto;
  }
}