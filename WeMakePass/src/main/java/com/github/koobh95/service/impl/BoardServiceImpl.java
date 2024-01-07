package com.github.koobh95.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.koobh95.data.model.entity.Board;
import com.github.koobh95.data.repository.BoardCategoryRepository;
import com.github.koobh95.data.repository.BoardRepository;
import com.github.koobh95.service.BoardService;

import lombok.RequiredArgsConstructor;

/**
 * 게시판을 조회하는 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@Service("boardService")
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	private final BoardRepository boardRepository;
	private final BoardCategoryRepository boardCategoryRepository;

	/**
	 * 게시판 이름을 기준으로 데이터 조회
	 * 
	 * @param keyword 검색어
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Board> searchByBoardName(String keyword) {		
		return boardRepository.findByBoardNameContaining(keyword);
	}

	/**
	 * 특정 게시판의 카테고리를 조회
	 * 
	 * @param boardNo 카테고리를 조회할 게시판의 고유 식별 번호
	 */
	@Override
	public List<String> getCategoryList(long boardNo) {
		return boardCategoryRepository.
				findByBoardNoOrderByBoardCategoryNo(boardNo).
				stream().
				map(e -> e.getCategoryName()).
				collect(Collectors.toList());
	}
}
