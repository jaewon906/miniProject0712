package com.project0712.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface BoardService {
    void save(BoardDTO boardDTO);

    BoardDTO findSpecificPost(BoardDTO boardDTO);

    List<BoardDTO> search(BoardDTO boardDTO);

    void deletePost(BoardDTO boardDTO);

    Page<BoardDTO> paging(Pageable pageable, int pageNum);
}
