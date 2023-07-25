package com.project0712.Board;

import com.project0712.Common.CookieConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


// @Controller는 주로 view를 반환하기 위해 사용
// @RestController는 주로 JSON 형태로 객체 데이터를 반환함. = @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardServiceImpl boardServiceImpl;
    private final CookieConfig cookieConfig;

    @GetMapping("/api/board/write")
    public BoardDTO writeForm(BoardDTO boardDTO) {
        return boardServiceImpl.findSpecificPost(boardDTO);
    }

    @PostMapping("/api/board/write") // MultiValueMap으로 사용할 수도 있다.
    public String write(BoardDTO board) { //게시글 생성
            boardServiceImpl.save(board);
            return "success";
    }

    @GetMapping("/api/board/browsePost") //특정 게시글 조회
    public BoardDTO browsePost(BoardDTO boardDTO) { // 특정 게시글 불러오기, 현재 요청을 한 번 보낼때 쿼리문이 3번 수행됨 -> 해결. 이유는 프론트단에서 렌더링이 3번 발생함.
        // 따라서 useEffect에 넣어놓음
        boardServiceImpl.updateHit(boardDTO);
        return boardServiceImpl.findSpecificPost(boardDTO);
    }

    @GetMapping("/api/board/searchResult") //게시글 서치
    public List<BoardDTO> search(BoardDTO boardDTO, Pageable pageable) {
        return boardServiceImpl.search(boardDTO, pageable);
    }

    @PostMapping("/api/board/deletePost") //게시글 삭제
    public void deletePost(BoardDTO boardDTO, HttpServletRequest request) { // 게시글 삭제
        Map<String, String> tokens = cookieConfig.requestCookie(request);
        boardServiceImpl.deletePost(boardDTO, tokens);
    }

    @GetMapping("/api/board") // 페이징 기능 + 게시판 들어갈 때 자동으로 게시글 띄움
    public Page<BoardDTO> paging(@PageableDefault(value = 1) Pageable pageable) {
        return boardServiceImpl.paging(pageable);
    }

}
