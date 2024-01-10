package com.github.koobh95.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.koobh95.data.model.entity.Post;
import com.github.koobh95.data.model.entity.mapping.PostMapping;

/**
 * - Post Entity 클래스와 대응되는 Repository 클래스
 * - 게시글을 조회할 때 삭제된 게시물은 조회 대상이 아니므로 삭제 날짜(deleteDate)가 IS NULL인 
 *  데이터로 조회 대상을 제한할 필요가 있다. 하지만 메서드 명명 방식으로는 IS NULL 키워드를 사용할 수 
 *  없기 때문에 JPQL을 사용하였다.
 *  
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
			@Param("boardNo") long boardNo);
	
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
	
	/**
	 * - 특정 게시판에서 게시글의 제목과 키워드가 부분 일치하는 게시글을 조회
	 * - 삭제된 게시글은 조회 대상에서 제외
	 * 
	 * @param pageable
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param keyword 검색어
	 * @return
	 */
	@Query("SELECT e FROM Post e "
			+ "WHERE e.boardNo = :boardNo AND e.title LIKE %:keyword% "
			+ "OR e.content LIKE %:keyword% AND e.deleteDate IS NULL")
	Page<PostMapping> searchByTitleAndContent(
			Pageable pageable,
			@Param("boardNo") long boardNo,
			@Param("keyword") String keyword);
	
	/**
	 * - 특정 게시판에서 게시글의 내용과 키워드가 부분 일치하는 게시글을 조회
	 * - 삭제된 게시글은 조회 대상에서 제외
	 * 
	 * @param pageable
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param keyword 검색어
	 * @return
	 */
	@Query("SELECT e FROM Post e "
			+ "WHERE e.boardNo = :boardNo AND e.title LIKE %:keyword% "
			+ "AND e.deleteDate IS NULL")
	Page<PostMapping> searchByTitle(
			Pageable pageable,
			@Param("boardNo") long boardNo,
			@Param("keyword") String keyword);

	/**
	 * - 특정 게시판에서 게시글의 제목 혹은 내용과 키워드가 부분 일치하는 게시글을 조회
	 * - 삭제된 게시글은 조회 대상에서 제외
	 * 
	 * @param pageable
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param keyword 검색어
	 * @return
	 */
	@Query("SELECT e FROM Post e "
			+ "WHERE e.boardNo = :boardNo AND e.content LIKE %:keyword% "
			+ "AND e.deleteDate IS NULL")
	Page<PostMapping> searchByContent(
			Pageable pageable,
			@Param("boardNo") long boardNo,
			@Param("keyword") String keyword);
	
	/**
	 * - 특정 게시판에서 게시글 제목과 키워드가 부분 일치하면서 특정 카테고리에 해당하는 게시글을 페이지 
	 *  단위로 조회한다.
	 * - 삭제된 게시글은 조회 대상에서 제외
	 * 
	 * @param pageable
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param category 조회할 카테고리
	 * @param keyword 검색어
	 * @return
	 */
	@Query("SELECT e FROM Post e "
			+ "WHERE e.boardNo = :boardNo AND e.category = :category "
			+ "AND e.title LIKE %:keyword% AND e.deleteDate IS NULL")
	Page<PostMapping> searchByCategoryAndTitle(
			Pageable pageable,
			@Param("boardNo") long boardNo,
			@Param("category") String category,
			@Param("keyword") String keyword);

	/**
	 * - 특정 게시판에서 게시글 내용과 키워드가 부분 일치하면서 특정 카테고리에 해당하는 게시글을 페이지 
	 *  단위로 조회한다.
	 * - 삭제된 게시글은 조회 대상에서 제외
	 * 
	 * @param pageable
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param category 조회할 카테고리
	 * @param keyword 검색어
	 * @return
	 */
	@Query("SELECT e FROM Post e "
			+ "WHERE e.boardNo = :boardNo AND e.category = :category "
			+ "AND e.content LIKE %:keyword% AND e.deleteDate IS NULL")
	Page<PostMapping> searchByCategoryAndContent(
			Pageable pageable,
			@Param("boardNo") long boardNo,
			@Param("category") String category,
			@Param("keyword") String keyword);

	/**
	 * - 특정 게시판에서 게시글 제목이나 내용과 키워드가 부분 일치하면서 특정 카테고리에 해당하는 게시글을 
	 *  페이지 단위로 조회한다.
	 * - 삭제된 게시글은 조회 대상에서 제외
	 * 
	 * @param pageable
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param category 조회할 카테고리
	 * @param keyword 검색어
	 * @return
	 */
	@Query("SELECT e FROM Post e "
			+ "WHERE e.boardNo = :boardNo AND e.category = :category "
			+ "AND e.title LIKE %:keyword% OR e.content LIKE %:keyword% "
			+ "AND e.deleteDate IS NULL")
	Page<PostMapping> searchByCategoryAndTitleAndContent(
			Pageable pageable,
			@Param("boardNo") long boardNo,
			@Param("category") String category,
			@Param("keyword") String keyword);
}
