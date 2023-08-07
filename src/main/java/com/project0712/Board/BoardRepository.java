package com.project0712.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Optional<BoardEntity> findByTitle(String title);

    Page<BoardEntity> searchByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByIdDesc(String title, String content, String author, Pageable pageable);
    @Modifying
    @Query(value = "update BoardEntity b set b.hit=b.hit+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

    @Modifying
    @Query(value = "update BoardEntity b set b.deleteFlag='Y' where b.id=:id")
    void updateDeleteFlag(@Param("id") Long id);

    Page<BoardEntity> findAllByDeleteFlag(Pageable pageable, String deleteFlag);


}
