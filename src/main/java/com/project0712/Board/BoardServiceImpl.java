package com.project0712.Board;

import com.project0712.Member.MemberDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional
    public List<BoardDTO> findAll() { //모든 글 불러오기
        List<BoardDTO> boardDTOList = new ArrayList<>();
        List<BoardEntity> boardEntityList = boardRepository.findAllByOrderByIdDesc();
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

    @Transactional
    public void updateHit(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);
        boardRepository.updateHits(boardEntity.getId());
    }

    @Override
    public List<BoardDTO> search(BoardDTO boardDTO) { //게시글 검색해서 찾기
        List<BoardDTO> boardDTOList = new ArrayList<>();
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);

        //containing = like 역할, ignoreCase = 대소문자 구분 x
        List<BoardEntity> boardEntities = boardRepository.searchByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByIdDesc(boardEntity.getTitle(), boardEntity.getTitle(), boardEntity.getTitle());

        for (BoardEntity boardEntity1 : boardEntities) {
            BoardDTO boardDTO1 = BoardDTO.EntityToDTO(boardEntity1);
            boardDTOList.add(boardDTO1);
        }
        return boardDTOList;
    }

    @Override
    public void deletePost(BoardDTO boardDTO) { //삭제 기능
        BoardEntity boardEntity = BoardEntity.DTOtoEntity(boardDTO);
        boardRepository.deleteById(boardEntity.getId());
    }

    @Override
    public Page<BoardDTO> paging(Pageable pageable, int pageNum) { //페이징 기능
        int page = pageable.getPageNumber() ;//-1을 하는 이유는 인덱스처럼 0번째부터 시작하기 때문
        int pageLimit = pageNum; // 한 페이지당 보여질 게시글 개수
        Page<BoardEntity> boardEntityPage = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        return boardEntityPage.map(BoardDTO::EntityToDTO); // BoardDTO::EntityToDTO = entity -> BoardDTO.EntityToDTO(entity)
    }
}
