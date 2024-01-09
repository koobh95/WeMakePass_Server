package com.github.koobh95.data.model.dto.response;

import java.util.List;

import com.github.koobh95.data.model.dto.PostDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 페이징으로 게시글 목록을 조회한 결과와 페이지 상태 정보를 반환하는데 사용되는 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@AllArgsConstructor
@Getter
public class PostPageResponse {
	private List<PostDTO> postList; // 조회한 게시글 리스트
	private int pageNo; // 읽은 페이지 번호
	private boolean last; // 현재 읽은 페이지가 마지막인지에 대한 여뷰
}
