package org.zerock.project1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.project1.dto.ReplyDTO;
import org.zerock.project1.entity.Board;
import org.zerock.project1.entity.Reply;
import org.zerock.project1.repository.ReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
  private final ReplyRepository repository;

  @Override
  public Long register(ReplyDTO dto) {
    Reply reply = dtoToEntity(dto);
    repository.save(reply);
    return reply.getid();
  }

  @Override
  public List<ReplyDTO> getList(Long id) {
    List<Reply> result = repository.getRepliesByBoardOrderByid(
        Board.builder().id(id).build()
    );

    return result.stream().map(r -> entityToDTO(r)).collect(Collectors.toList());
  }

  @Override
  public void modify(ReplyDTO dto) {
    Reply reply = dtoToEntity(dto);
    repository.save(reply);
  }

  @Override
  public void remove(Long id) {
    repository.deleteById(id);
  }
}