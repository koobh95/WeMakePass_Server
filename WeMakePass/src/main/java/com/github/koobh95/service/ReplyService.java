package com.github.koobh95.service;

import java.util.List;

import com.github.koobh95.data.model.dto.ReplyDTO;
import com.github.koobh95.data.model.dto.request.ReplyWriteRequest;

/**
 * 댓글과 관련된 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-11
 */
public interface ReplyService {
	// 특정 게시글의 댓글 목록을 조회
	List<ReplyDTO> replyList(long postNo);
	// 새로운 댓글 작성
	void write(ReplyWriteRequest request);
	// 특정 댓글 삭제
	void delete(long replyNo);
}
