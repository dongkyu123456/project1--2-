package org.zerock.project1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.project1.dto.BoardDTO;
import org.zerock.project1.dto.PageRequestDTO;
import org.zerock.project1.service.BoardService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
  private final BoardService service;

  @RequestMapping({"", "/"})
  public String controllerHome() {
    return "redirect:/board/list";
  }

  @RequestMapping("/list")
  public String list(PageRequestDTO pageRequestDTO, Model model){
    log.info("/board/list... pageRequestDTO:" + pageRequestDTO);
    model.addAttribute("result", service.getList(pageRequestDTO));
  return "/board/board";
  }

  @GetMapping("/register")
  public void register(){log.info("register...");}

  @PostMapping("/register")
  public String registerPost(BoardDTO dto, RedirectAttributes ra){
    log.info("register Post ..." + dto);
    Long bno = service.register(dto);
    ra.addFlashAttribute("msg",bno + " 등록");
    return "redirect:/board/list";
  }

  @GetMapping({"read", "modify"})
  public void read(PageRequestDTO pageRequestDTO, Model model, Long bno) {
    log.info("/board/read..... bno:" + bno);
    BoardDTO dto = service.get(bno);
    model.addAttribute("dto", dto);
  }

  @PostMapping("/remove")
  public String remove(long bno, RedirectAttributes ra, PageRequestDTO pageRequestDTO ){
    log.info("remove..."+bno);
    service.removeWithReplies(bno);
    if(service.getList(pageRequestDTO).getDtoList().size()==0 && pageRequestDTO.getPage()!=1 ){
      pageRequestDTO.setPage(pageRequestDTO.getPage()-1);
    };

    ra.addFlashAttribute("msg", bno+ " 삭제");
    ra.addAttribute("page", pageRequestDTO.getPage());
    ra.addAttribute("type", pageRequestDTO.getType());
    ra.addAttribute("keyword", pageRequestDTO.getKeyword());
    return "redirect:/board/list";
  }

  @PostMapping("/modify")
  public String modifyPost(BoardDTO dto, RedirectAttributes ra, PageRequestDTO pageRequestDTO ){
    log.info("modifyPost..."+dto);
    service.modify(dto);
    ra.addFlashAttribute("msg", dto.getBno());
    ra.addAttribute("bno", dto.getBno());
    ra.addAttribute("page", pageRequestDTO.getPage());
    ra.addAttribute("type", pageRequestDTO.getType());
    ra.addAttribute("keyword", pageRequestDTO.getKeyword());
    return "redirect:/board/read";
  }
}