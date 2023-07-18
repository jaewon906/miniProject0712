package com.project0712.Board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public BoardDTO findSpecificPost(BoardDTO boardDTO) { //특정 글 누르면 해당하는 데이터 불러오기
        Optional<BoardEntity> byId = boardRepository.findById(boardDTO.getId());
        return byId.map(BoardDTO::EntityToDTO).orElse(null);
    }

    @Override
    public List<BoardDTO> search(BoardDTO boardDTO) { //게시글 검색해서 찾기
        List<BoardDTO> boardDTOList = new ArrayList<>();
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);

        //containing = like 역할, ignoreCase = 대소문자 구분 x
        List<BoardEntity> boardEntities = boardRepository.searchByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrAuthorContainingIgnoreCase(boardEntity.getTitle(), boardEntity.getTitle(), boardEntity.getTitle());

        for(BoardEntity boardEntity1 : boardEntities){
            BoardDTO boardDTO1 = BoardDTO.EntityToDTO(boardEntity1);
            boardDTOList.add(boardDTO1);
        }
        return boardDTOList;
    }

}
