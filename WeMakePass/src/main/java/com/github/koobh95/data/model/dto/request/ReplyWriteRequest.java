package com.github.koobh95.data.model.dto.request;

import com.github.koobh95.data.model.entity.Reply;

import lombok.Getter;

/**
 * 새로운 댓글을 작성하기 위한 데이터들을 가지는 DTO 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-12
 */
@Getter
public class ReplyWriteRequest {
    private long postNo; // 댓글을 작성할 게시글의 고유 식별 번호
    private long parentReplyNo; // 답글일 경우 상위 댓글의 고유 식별 번호
    private String writerId; // 댓글 작성자의 ID
    private String content; // 댓글 내용
    
    /**
     *  새로운 댓글을 작성하기 위한 데이터를 가진 ReplyWriteRequest 객체를 Entity 객체로 
     * 변환하여 반환.
     */
    public static Reply toEntity(ReplyWriteRequest request) {
    	return new Reply(request.parentReplyNo,
    			request.postNo,
    			request.writerId,
    			request.content);
    }
}
