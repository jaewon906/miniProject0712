package com.project0712.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BoardService {
    void save(BoardDTO boardDTO);

    BoardDTO findSpecificPost(BoardDTO boardDTO);

    List<BoardDTO> search(BoardDTO boardDTO, Pageable pageable);

    boolean deletePost(BoardDTO boardDTO);

    void updateHit(BoardDTO boardDTO);

    Page<BoardDTO> paging(Pageable pageable);
}
