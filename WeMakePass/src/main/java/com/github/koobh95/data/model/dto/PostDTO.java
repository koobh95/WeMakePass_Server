package com.github.koobh95.data.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Builder;
import lombok.Getter;

/**
 * Post Entity 클래스의 멤버 변수 중 게시글 목록 조회에 필요한 데이터만으로 구성된 DTO 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@Getter
@Builder
public class PostDTO {
	private long postNo; // 게시글의 고유 식별 번호
	private String category; // 게시글의 카테고리
	private String nickname; // 게시글 작성자의 닉네임
	private String title; // 게시글 제목
	@JsonFormat(shape = JsonFormat.Shape.STRING, 
			pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime regDate; // 게시글 작성 날짜
	private long hit; // 조회수
	private int replyCount;	// 게시글에 작성된 댓글 수
}
