package com.project0712.Board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public void save(BoardDTO boardDTO) { //글 저장
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

    public List<BoardDTO> findAll() { //모든 글 불러오기
        List<BoardDTO> boardDTOList = new ArrayList<>();
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        for (BoardEntity boardEntity : boardEntityList) {

            boardDTOList.add(BoardDTO.EntityToDTO(boardEntity));
        }
        return boardDTOList;

    }

    ;

}
