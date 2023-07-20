package com.project0712.Board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Controller는 주로 view를 반환하기 위해 사용
// @RestController는 주로 JSON 형태로 객체 데이터를 반환함. = @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardServiceImpl boardServiceImpl;



    @GetMapping("/api/board/write")
    public BoardDTO writeForm(BoardDTO boardDTO) {
        return boardServiceImpl.findSpecificPost(boardDTO);
    }

    @PostMapping("/api/board/write") // MultiValueMap으로 사용할 수도 있다.
    public Object write(@Validated BoardDTO board, BindingResult bindingResult) { //@Validated를 붙혀주면 validate를 구현한 클래스 호출없어도 된다.
        if (!bindingResult.hasErrors()) {
            boardServiceImpl.save(board);
            return "success";
        } else {
            return bindingResult.getAllErrors();
        }
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
    public void deletePost(BoardDTO boardDTO) { // 게시글 삭제
        boardServiceImpl.deletePost(boardDTO);
    }

    @GetMapping("/api/board") // 페이징 기능 + 게시판 들어갈 때 자동으로 게시글 띄움
    public Page<BoardDTO> paging(@PageableDefault(value = 1) Pageable pageable) {
        //        int blockLimit = 10; //하단 페이지 번호 개수
//        int startPage = (((int)Math.ceil((double)pageable.getPageNumber() / blockLimit))-1) * blockLimit + 1;
//        int endPage = ((startPage - blockLimit -1) < pagingList.getTotalPages()) ? startPage + blockLimit -1 :pagingList.getTotalPages();

        return boardServiceImpl.paging(pageable);
    }

}
