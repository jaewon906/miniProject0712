package com.project0712.Board;

import com.project0712.Auth.TokenConfig;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final TokenConfig tokenConfig;


    @Override
    public void save(BoardDTO boardDTO) { //글 저장
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);
        boardRepository.save(boardEntity);
    }


    @Override
    public BoardDTO findSpecificPost(BoardDTO boardDTO) { //특정 글 누르면 해당하는 데이터 불러오기
        Optional<BoardEntity> byId = boardRepository.findById(boardDTO.getId());
        return byId.map(BoardDTO::EntityToDTO).orElse(null);
    }

    @Transactional
    public void updateHit(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);
        boardRepository.updateHits(boardEntity.getId());
    }

    @Override
    public List<BoardDTO> search(BoardDTO boardDTO, Pageable pageable) { //게시글 검색해서 찾기
        List<BoardDTO> boardDTOList = new ArrayList<>();
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);

        //containing = like 역할, ignoreCase = 대소문자 구분 x
        Page<BoardEntity> boardEntities = boardRepository.searchByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByIdDesc(boardEntity.getTitle(), boardEntity.getTitle(), boardEntity.getTitle(), pageable);

        for (BoardEntity boardEntity1 : boardEntities) {
            BoardDTO boardDTO1 = BoardDTO.EntityToDTO(boardEntity1);
            boardDTOList.add(boardDTO1);
        }
        return boardDTOList;
    }

    @Override
    public boolean deletePost(BoardDTO boardDTO, Map<String, String> tokens) { //삭제 기능
        boolean validateToken = tokenConfig.validateToken(tokens);
        if(validateToken){
            BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);
            boardRepository.deleteById(boardEntity.getId());
            return true;
        }
        else return false;
    }

    @Override
    public Page<BoardDTO> paging(Pageable pageable) { //페이징 기능
        Page<BoardEntity> boardEntityPage = boardRepository.findAll(pageable);
        return boardEntityPage.map(BoardDTO::EntityToDTO); // BoardDTO::EntityToDTO = entity -> BoardDTO.EntityToDTO(entity)
    }
}
