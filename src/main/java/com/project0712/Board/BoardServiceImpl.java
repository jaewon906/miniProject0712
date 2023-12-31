package com.project0712.Board;

import com.project0712.Security.TokenConfig;
import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final TokenConfig tokenConfig;


    @Override
    public void save(BoardDTO boardDTO) { //글 저장
        //로그인한 사용자에 해당하는 pk값을 가져오고 boardEntity에 저장하는 로직 작성예정
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);
        boardRepository.save(boardEntity);
        log.info("time : {}",boardEntity.getUpdatedTime());
    }


    @Override
    public BoardDTO findSpecificPost(BoardDTO boardDTO) { //특정 글 누르면 해당하는 데이터 불러오기
        Optional<BoardEntity> byId = boardRepository.findById(boardDTO.getId());
        return byId.map(BoardDTO::EntityToDTO).orElse(null);
    }

    @Override
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
    @Transactional
    public boolean deletePost(BoardDTO boardDTO) { //삭제 기능

            BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);
            try{
                boardRepository.updateDeleteFlag(boardEntity.getId());
                return true;
            }catch (TransactionalException e){
                return false;
            }

    }

    @Override
    public Page<BoardDTO> paging(Pageable pageable) { //페이징 기능
        Page<BoardEntity> boardEntityPage = boardRepository.findAllByDeleteFlag(pageable, "N");
        return boardEntityPage.map(BoardDTO::EntityToDTO); // BoardDTO::EntityToDTO = entity -> BoardDTO.EntityToDTO(entity)
    }
}
