package com.project0712.Board;

import com.project0712.Member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Optional<BoardEntity> findByTitle(String title);

    List<BoardEntity> findAllByOrderByIdDesc();

    List<BoardEntity> searchByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByIdDesc(String title, String content, String author);
    @Modifying
    @Query(value = "update BoardEntity b set b.hit=b.hit+1 where b.id=:id")
    void updateHits(@Param("id") Long id);


}
