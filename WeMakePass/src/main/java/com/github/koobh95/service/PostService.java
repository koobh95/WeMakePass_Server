package com.github.koobh95.service;

import com.github.koobh95.data.model.dto.PostDetailDTO;
import com.github.koobh95.data.model.dto.request.PostWriteRequest;
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

	// 새로운 게시글을 작성
	void write(PostWriteRequest postWriteRequest);
	
	// 특정 게시글 조회
	PostDetailDTO postDetail(long postNo);
	
	// 특정 게시판에서 특정 게시글의 제목과 키워드가 부분 일치하는 게시글 조회
	PostPageResponse searchTitle(int pageNo, long boardNo, String keyword);
	// 특정 게시판에서 특정 게시글의 내용과 키워드가 부분 일치하는 게시글 조회
	PostPageResponse searchContent(int pageNo, long boardNo, String keyword);
	// 특정 게시판에서 특정 게시글의 제목이나 내용 키워드가 부분 일치하는 게시글 조회
	PostPageResponse searchTitleAndContent(int pageNo, long boardNo, 
			String keyword);
	// 특정 게시판에서 특정 게시글의 제목과 키워드가 부분 일치하면서 특정 카테고리에 해당하는 게시글 조회  
	PostPageResponse searchCategoryAndTitle(int pageNo, long boardNo, 
			String category, String keyword);
	// 특정 게시판에서 특정 게시글의 내용과 키워드가 부분 일치하면서 특정 카테고리에 해당하는 게시글 조회
	PostPageResponse searchCategoryAndContent(int pageNo, long boardNo, 
			String category, String keyword);
	// 특정 게시판에서 특정 게시글의 제목 혹은 내용과 키워드가 부분 일치하면서 특정 카테고리에 해당하는 게시글 조회
	PostPageResponse searchCategoryAndTitleAndContent(int pageNo, 
			long boardNo, String category, String keyword);
}
