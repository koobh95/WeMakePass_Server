package com.github.koobh95.data.model.dto.request;

import java.time.LocalDateTime;

import com.github.koobh95.data.model.entity.Post;

import lombok.Getter;

/**
 * 새로운 게시글을 작성하기 위한 데이터들을 가지는 DTO 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-10
 */
@Getter
public class PostWriteRequest {
    private long boardNo; // 게시글이 작성될 게시판의 고유 식별 번호
    private String writer; // 작성자의 ID
    private String category; // 작성할 카테고리
    private String title; // 제목
    private String content; // 게시글 내용
    
    // DTO 객체를 Entity 객체로 변환하여 반환
    public Post toEntity() {
    	return Post.create(
    			boardNo,
    			category,
    			writer,
    			title,
    			content,
    			LocalDateTime.now());
    }
}
