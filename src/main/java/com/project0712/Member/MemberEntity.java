package com.project0712.Member;

import com.project0712.Board.BoardEntity;
import com.project0712.Common.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "memberInfo")
public class MemberEntity extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String userId;
    @Column(nullable = false)
    private String userPassword;
    @Column(nullable = false, unique = true)
    private String userNickname;
    @Column(nullable = false, unique = true)
    private String userEmail;
    @Column(nullable = false)
    private String userTel;
    @Column(nullable = false)
    private String userAddress;
    @Column(nullable = false)
    private String userSex;
    @Column
    private MemberRole role;

    //PK(JoinColumn)���� NULL�� ���� �ڽ��� ��ư�ü��� �Ͽ� ����� ���� ���� ��ü orphanremoval�� ��� ��ü�� ����
    //cascade.ALL = PERSIST(�θ� �ڽ��� �ѹ��� ����ȭ(DB������)) + REMOVE(�θ� ������ ������ �ڽĵ� ����)
    @OneToMany(mappedBy = "memberEntity", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<BoardEntity> boardEntity = new ArrayList<>();

    public static MemberEntity DTOToEntity(MemberDTO memberDTO){

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setId(memberDTO.getId());
        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setUserPassword(BCrypt.hashpw(memberDTO.getUserPassword(),BCrypt.gensalt()));
        memberEntity.setUserNickname(memberDTO.getUserNickname());
        memberEntity.setUserEmail(memberDTO.getUserEmail());
        memberEntity.setUserTel(memberDTO.getUserTel());
        memberEntity.setUserAddress(memberDTO.getUserAddress());
        memberEntity.setUserSex(memberDTO.getUserSex());
        memberEntity.setRole(memberDTO.getRole());

        return memberEntity;
    }

    public static MemberEntity DTOToEntityOnlyUseDuplicateValidation(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setUserId(memberDTO.getUserId());
        memberEntity.setUserNickname(memberDTO.getUserNickname());
        memberEntity.setUserEmail(memberDTO.getUserEmail());

        return memberEntity;
    }
}
