package com.project0712.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    void save(BoardDTO boardDTO);

    BoardDTO findSpecificPost(BoardDTO boardDTO);

    List<BoardDTO> search(BoardDTO boardDTO, Pageable pageable);

    void deletePost(BoardDTO boardDTO);

    Page<BoardDTO> paging(Pageable pageable);
}
