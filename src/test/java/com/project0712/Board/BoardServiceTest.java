package com.project0712.Board;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestConstructor;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class BoardServiceTest {

    private final BoardRepository boardRepository;

    @Test
    public void 게시글저장() throws Exception {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle("안녕");
        boardDTO.setContent("자유게시판");
        boardDTO.setCategory("안녕!!");


        assertEquals(boardDTO.getTitle(), "안녕");
        assertEquals(boardDTO.getContent(), "자유게시판");
        assertEquals(boardDTO.getCategory(), "안녕!!");


        BoardEntity boardEntity1 = BoardEntity.DTOtoEntity(boardDTO);


        assertEquals(boardEntity1.getTitle(), "안녕");
        assertEquals(boardEntity1.getContent(), "자유게시판");
        assertEquals(boardEntity1.getCategory(), "안녕!!");


        boardRepository.save(boardEntity1); //DB확인

        Optional<BoardEntity> hi = boardRepository.findByTitle("안녕");//DB에 저장되었는지 확인하기

        if(hi.isPresent()){
            System.out.println(hi);
        }
        else{
           throw new Exception("존재하지 않습니다.");
        }


    }
    @Test
    void 특정게시글불러오기() throws Exception{
            Optional<BoardEntity> byId = boardRepository.findById(3L);
            assertEquals("아니 왜안대\"?",byId.get().getTitle());
    }

    @Test
    public void 게시글검색하기() throws Exception{



    }


    @Test
    void paging() {
        int pageNum = 50;
        int pageLimit = pageNum; // 한 페이지당 보여질 게시글 개수
        Pageable pageable = PageRequest.of(0, pageLimit, Sort.by(Sort.Direction.DESC, "id"));
        Page<BoardEntity> boardEntityPage = boardRepository.findAll(pageable);
        Page<BoardDTO> map = boardEntityPage.map(entity -> new BoardDTO());
        assertEquals(50,map.getSize());
    }


}
