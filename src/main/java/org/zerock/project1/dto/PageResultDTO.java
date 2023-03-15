package org.zerock.project1.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class PageResultDTO<DTO, EN> {
  private List<DTO> dtoList;

  private int totalPage;
  private int page;
  private int size;

  private int start, end;
  private boolean prev, next;

  private List<Integer> pageList; 

  public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
    dtoList = result.stream().map(fn).collect(Collectors.toList());
    totalPage = result.getTotalPages();
    makePageList(result.getPageable());
  }
  private void makePageList(Pageable pageable) {
    page = pageable.getPageNumber() + 1;
    size = pageable.getPageSize();

    int tmpEnd = (int)(Math.ceil(page/10.0))*10;
    end = totalPage > tmpEnd ? tmpEnd : totalPage;
    start = tmpEnd - 9;

    prev = start > 1;
    next = totalPage > tmpEnd;
    pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
  }
}