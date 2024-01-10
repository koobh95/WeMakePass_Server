package com.github.koobh95.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.koobh95.data.model.entity.Post;
import com.github.koobh95.data.model.entity.mapping.PostMapping;

/**
 * Post Entity 클래스와 대응되는 Repository 클래스
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
public interface PostRepository extends JpaRepository<Post, Long> {
	/**
	 * 특정 게시글을 조회
	 * 
	 * @param postNo 조회할 게시글의 고유 식별 번호
	 * @return
	 */
	Post findByPostNo(long postNo);
	
	/**
	 * - 특정 게시판의 게시글을 조회
	 * - 삭제된 게시글은 조회 대상에서 제외
	 * 
	 * @param pageable
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @return
	 */
	@Query("SELECT e FROM Post e "
			+ "WHERE e.boardNo = :boardNo "
			+ "AND e.deleteDate IS NULL")
	Page<PostMapping> findByBoardNo(Pageable pageable, 
			@Param("boardNo") long boardNo); // OrderByPostNo
	
	/**
	 * - 특정 게시판, 특정 카테고리의 게시글 조회
	 * - 삭제된 게시글은 조회 대상에서 제외
	 * 
	 * @param pageable
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param category 조회할 카테고리
	 * @return
	 */
	@Query("SELECT e FROM Post e "
			+ "WHERE e.boardNo = :boardNo AND e.deleteDate IS NULL "
			+ "AND e.category = :category")
	Page<PostMapping> findByBoardNoAndCategory(
			Pageable pageable, 
			@Param("boardNo") long boardNo, 
			@Param("category") String category);
}
