package com.project0712.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByuserId(String userId); //find search 차이점 : find는 대소문자 구분 o, 와일드카드 문자 사용 x / search는 반대
    Optional<MemberEntity> findByuserNickname(String userNickname);
    Optional<MemberEntity> findByuserEmail(String userEmail);

    void deleteAllByUserId(String userId);

}
