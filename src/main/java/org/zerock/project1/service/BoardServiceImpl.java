package org.zerock.project1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.project1.dto.BoardDTO;
import org.zerock.project1.dto.PageRequestDTO;
import org.zerock.project1.dto.PageResultDTO;
import org.zerock.project1.entity.Board;
import org.zerock.project1.entity.Member;
import org.zerock.project1.repository.BoardRepository;
import org.zerock.project1.repository.ReplyRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
  private final BoardRepository repository;
  private final ReplyRepository replyRepository;
  @Override
  public Long register(BoardDTO dto) {
    log.info("dto: " + dto);
    Board board = dtoToEntity(dto);
    repository.save(board);
    return board.getid();
  }

  @Override
  public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
    log.info("pageRequestDTO: "+ pageRequestDTO);
    Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0], (Member)en[1],(Long)en[2]));
//    Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageable(
//        Sort.by("id").descending()));
    Page<Object[]> result = repository.searchPage(
        pageRequestDTO.getType(),
        pageRequestDTO.getKeyword(),
        pageRequestDTO.getPageable(Sort.by("id").descending())
    );
    return new PageResultDTO<>(result, fn);
  }

  @Override
  public BoardDTO get(Long id) {
    Object[] arr = (Object[]) repository.getBoardByid(id);
    return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
  }
  @Transactional
  @Override
  public void removeWithReplies(Long id) {
    replyRepository.deleteByid(id);
    repository.deleteById(id);
  }

  @Override
  public void modify(BoardDTO dto) {
    Optional<Board> result = repository.findById(dto.getid());
    if (result.isPresent()) {
      Board board = result.get();
      board.changeTitle(dto.getTitle());
      board.changeContent(dto.getContent());
      repository.save(board);
    }
  }
}