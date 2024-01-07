package com.github.koobh95.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.koobh95.annotation.LoginRequired;
import com.github.koobh95.data.model.entity.Board;
import com.github.koobh95.service.BoardService;

import lombok.RequiredArgsConstructor;

/**
 * 게시판을 조회하는 API를 제공
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	
	/**
	 * 게시판 이름을 기준으로 데이터 조회
	 * 
	 * @param keyword 검색어
	 * @return
	 */
	@LoginRequired
	@GetMapping("/search")
	public List<Board> search(
			@RequestParam String keyword) {
		return boardService.searchByBoardName(keyword);
	}
	
	/**
	 * 특정 게시판의 카테고리를 조회
	 * 
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @return
	 */
	@LoginRequired
	@GetMapping("/category")
	public List<String> categoryList(
			@RequestParam long boardNo) {
		return boardService.getCategoryList(boardNo);
	}
}
