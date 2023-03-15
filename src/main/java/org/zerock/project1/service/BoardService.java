package org.zerock.project1.service;

import org.zerock.project1.dto.BoardDTO;
import org.zerock.project1.dto.PageRequestDTO;
import org.zerock.project1.dto.PageResultDTO;
import org.zerock.project1.entity.Board;
import org.zerock.project1.entity.Member;

public interface BoardService {
  Long register(BoardDTO dto);

  PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

  BoardDTO get(Long id);

  void removeWithReplies(Long id);

  void modify(BoardDTO dto);

  default Board dtoToEntity(BoardDTO dto) {
    Member member = Member.builder().email(
        dto.getWriterEmail()).build();
    Board board = Board.builder()
        .id(dto.getid())
        .title(dto.getTitle())
        .content(dto.getContent())
        .writer(member)
        .build();
    return board;
  }
  default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
    BoardDTO dto = BoardDTO.builder()
        .id(board.getid())
        .title(board.getTitle())
        .content(board.getContent())
        .created_at(board.getcreated_at())
        .modDate(board.getModDate())
        .writerEmail(member.getEmail())
        .nickname(member.getName())
        .replyCount(replyCount.intValue())
        .build();
    return dto;
  }
}