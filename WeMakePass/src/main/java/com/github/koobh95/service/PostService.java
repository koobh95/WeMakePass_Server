package com.github.koobh95.service;

import com.github.koobh95.data.model.dto.response.PostPageResponse;
/**
 * 게시글과 관련된 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
public interface PostService {
	// 특정 게시판의 게시글을 페이지 단위로 조회
	PostPageResponse postList(long boardNo, int pageNo);
	// 특정 게시판, 특정 카테고리의 게시글을 페이지 단위로 조회
	PostPageResponse postListByCategory(long boardNo, int pageNo, 
			String category);
}
