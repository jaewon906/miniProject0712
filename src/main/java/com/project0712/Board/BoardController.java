package com.project0712.Board;

import com.project0712.Member.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

// @Controller는 주로 view를 반환하기 위해 사용
// @RestController는 주로 JSON 형태로 객체 데이터를 반환함. = @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardServiceImpl boardServiceImpl;

    @GetMapping("api/board") // 모든 글 불러오기
    public List<BoardDTO> loadBoardListAll() {
        return boardServiceImpl.findAll();
    }

    @GetMapping("/api/board/write")
    public BoardDTO writeForm(BoardDTO boardDTO) {
        return boardServiceImpl.findSpecificPost(boardDTO);
    }

    @PostMapping("/api/board/write") // MultiValueMap으로 사용할 수도 있다.
    public String write(BoardDTO board) {
        boardServiceImpl.save(board);
        return "redirect:/board";
    }

    @GetMapping("/api/board/browsePost")
    public BoardDTO browse(BoardDTO boardDTO) { // 특정 게시글 불러오기
        return boardServiceImpl.findSpecificPost(boardDTO);
    }

    @GetMapping("/api/board/searchResult")
    public List<BoardDTO> search(BoardDTO boardDTO){
        return boardServiceImpl.search(boardDTO);
    }
}
