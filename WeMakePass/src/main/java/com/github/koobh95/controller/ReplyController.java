package com.github.koobh95.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.koobh95.annotation.LoginRequired;
import com.github.koobh95.data.model.dto.ReplyDTO;
import com.github.koobh95.service.ReplyService;

import lombok.RequiredArgsConstructor;

/**
 * 댓글 관련 데이터를 조회, 삽입, 삭제하는 API를 제공.
 * 
 * @author BH-Ku
 * @since 2024-01-11
 */
@RestController
@RequestMapping("/api/reply/")
@RequiredArgsConstructor
public class ReplyController {
	private final ReplyService replyService;

	/**
	 * 특정 게시글의 댓글 목록을 조회
	 * 
	 * @param postNo 조회할 게시글의 고유 식별 번호
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "list")
	public List<ReplyDTO> replyList(@RequestParam long postNo) {
		return replyService.replyList(postNo);
	}
}
