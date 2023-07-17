package com.project0712.Board;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setTitle("천재");
        boardDTO.setContent("천재");

        List<BoardDTO> boardDTOList = new ArrayList<BoardDTO>();
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);

        //containing = like 역할, ignoreCase = 대소문자 구분 x
        List<BoardEntity> boardEntities = boardRepository.searchByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrAuthorContainingIgnoreCase(boardEntity.getTitle(), boardEntity.getTitle(), boardEntity.getTitle());

        for (BoardEntity boardEntity1 : boardEntities) {
            BoardDTO boardDTO1 = BoardDTO.EntityToDTO(boardEntity1);
            boardDTOList.add(boardDTO1);
        }

        assertEquals(1,boardEntities.size());


    }

}
