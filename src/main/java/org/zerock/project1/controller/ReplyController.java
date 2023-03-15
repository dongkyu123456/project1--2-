package org.zerock.project1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.project1.dto.ReplyDTO;
import org.zerock.project1.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService service;

    @GetMapping(value = "/board/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("id") Long id) {
        log.info("id: "+id);
        return new ResponseEntity<>(service.getList(id), HttpStatus.OK);
    }

    @PostMapping({"","/"})
    public ResponseEntity<Long> register(@RequestBody ReplyDTO dto) {
        log.info("reply dto: " + dto);
        Long id = service.register(dto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> modify(@RequestBody ReplyDTO dto) {
        log.info("reply dto: " + dto);
        service.modify(dto);
        return new ResponseEntity<>(dto.getid(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        log.info("reply id: " + id);
        service.remove(id);
        return new ResponseEntity<>(id+"", HttpStatus.OK);
    }
}