package com.example.CUSHProject.repository;

import com.example.CUSHProject.entity.BoardCategoryEntity;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findByNoticeAndContentContaining(int notice, String keyword, Pageable pageable);
    Page<BoardEntity> findByNoticeAndTitleContaining(int notice, String keyword, Pageable pageable);
    Page<BoardEntity> findByNoticeAndWriterContaining(int notice, String keyword, Pageable pageable);

    List<BoardEntity> findByNoticeAndCategory(int notice,BoardCategoryEntity categoryId);
    List<BoardEntity> findByNoticeAndCategoryAndContentContaining(int notice, BoardCategoryEntity categoryId, String keyword);
    List<BoardEntity> findByNoticeAndCategoryAndTitleContaining(int notice, BoardCategoryEntity categoryId, String keyword);
    List<BoardEntity> findByNoticeAndCategoryAndWriterContaining(int notice, BoardCategoryEntity categoryId, String keyword);


}
