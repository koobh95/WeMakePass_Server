package com.github.koobh95.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.koobh95.data.model.entity.Reply;

/**
 * Reply Entity 클래스와 대응되는 Repository 클래스
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {
	// 기본 키로 특정 댓글을 조회
	Reply findByReplyNo(long replyNo);
	
	/**
	 * 특정 게시글의 댓글 목록을 조회하되 대댓글이 아닌 댓글을 대상으로 조회.
	 * 
	 * @param postNo 조회할 게시글의 고유 식별 번호
	 * @return
	 */
	@Query("SELECT e FROM Reply e "
			+ "WHERE e.postNo = :postNo AND e.parentReplyNo = -1 "
			+ "ORDER BY e.replyNo")
	List<Reply> findByPostNo(@Param("postNo") long postNo);
}
