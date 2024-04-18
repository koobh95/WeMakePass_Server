package com.github.koobh95.data.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Post Entity 클래스의 멤버 변수 중 게시글을 조회했을 때 보여질 데이터만으로 재구성한 DTO 클래스. 
 * 
 * @author BH-Ku
 * @since 2024-01-10
 */
@Getter
@AllArgsConstructor
public class PostDetailDTO {
    private String category; // 게시글의 카테고리
    private String nickname; // 작성자의 닉네임
    private String title; // 게시글 제목
    private String content; // 게시글 내용
	@JsonFormat(shape = JsonFormat.Shape.STRING, 
			pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime regDate; // 게시글 작성 날짜
    private long hit; // 조회수
}
