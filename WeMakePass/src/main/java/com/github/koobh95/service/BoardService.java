package com.github.koobh95.service;

import java.util.List;

import com.github.koobh95.data.model.entity.Board;

/**
 * 게시판을 조회하는 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
public interface BoardService {
	// 게시판 이름을 기준으로 데이터 조회
	public List<Board> searchByBoardName(String keyword);
}
