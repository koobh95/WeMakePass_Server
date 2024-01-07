package com.github.koobh95.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.koobh95.data.model.entity.Board;

/**
 * Board 클래스와 대응되는 Repository 클래스
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
public interface BoardRepository extends JpaRepository<Board, Long> {
	// BoardName과 특정 문자열이 일치하는 데이터들을 조회
	List<Board> findByBoardNameContaining(String boardName);
}
