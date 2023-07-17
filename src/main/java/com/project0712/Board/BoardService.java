package com.project0712.Board;

import org.springframework.util.MultiValueMap;

import java.util.List;

public interface BoardService {
    void save(BoardDTO boardDTO);

    BoardDTO findSpecificPost(BoardDTO boardDTO);

    List<BoardDTO> search(BoardDTO boardDTO);
}
