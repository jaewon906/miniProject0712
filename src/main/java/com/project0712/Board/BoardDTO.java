package com.project0712.Board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String title; //게시글 제목
    private String category; // 게시글 카테고리
    private String content; // 게시글 내용
    private String author; // 작성자
    private int hit; // 조회수
    private int boardLikes; //좋아요

    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    public static BoardDTO EntityToDTO(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setId(boardEntity.getId());
        boardDTO.setTitle(boardEntity.getTitle());
        boardDTO.setCategory(boardEntity.getCategory());
        boardDTO.setContent(boardEntity.getContent());
        boardDTO.setAuthor(boardEntity.getAuthor());
        boardDTO.setHit(boardEntity.getHit());
        boardDTO.setBoardLikes(boardEntity.getBoardLikes());

        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());

        return  boardDTO;
    }
}
