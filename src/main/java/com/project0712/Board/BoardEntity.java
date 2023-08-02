package com.project0712.Board;

import com.project0712.Common.TimeBaseEntity;
import com.project0712.Member.MemberEntity;
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
    @Column(name = "board_id")
    private Long id; //pk
    @Column(nullable = false,length = 100)
    private String title; //게시글 제목
    @Column(nullable = false)
    private String category; // 게시글 카테고리
    @Column(nullable = false, length = 1000)
    private String content; // 게시글 내용
    @Column
    private String author; // 작성자
    @Column
    private int hit; // 조회수
    @Column
    private int boardLikes; //좋아요

    @ManyToOne(fetch = FetchType.LAZY) //Eager = 연관관계 있는 엔티티들 모두 가져올때 (근데 거의안씀) / Lazy = getter로 가져올 때
    @JoinColumn(name = "member_id")//@JoinColumn 어노테이션은 외래 키를 매핑할 때 사용하는 어노테이션, 이름 지정을 위해 쓰이며 생략가능
    private MemberEntity memberEntity;

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
