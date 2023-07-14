package com.project0712.Board;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Board_Info")
@Getter
@Setter
public class BoardEntity extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title; //게시글 제목
    @Column
    private String category; // 게시글 카테고리
    @Column(nullable = false)
    private String content; // 게시글 내용
    @Column
    private String author; // 작성자
    @Column
    private String hit; // 조회수
    @Column
    private String boardLikes; //좋아요

    public static BoardEntity DTOtoEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setId(boardDTO.getId());
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setCategory(boardDTO.getCategory());
        boardEntity.setContent(boardDTO.getContent());
        boardEntity.setAuthor(boardDTO.getAuthor());
        boardEntity.setHit(boardDTO.getHit());
        boardEntity.setBoardLikes(boardDTO.getBoardLikes());

        return boardEntity;
    }
}
