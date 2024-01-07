package com.github.koobh95.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.koobh95.data.model.entity.BoardCategory;
import com.github.koobh95.data.model.entity.mapping.CategoryNameMapping;

/**
 * BoardCategory 클래스와 대응되는 Repository 클래스
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {
	// 특정 게시판의 식별 번호로 카테고리 목록을 조회하되 오름차순으로 조회
	List<CategoryNameMapping> findByBoardNoOrderByBoardCategoryNo(long boardNo);
}
