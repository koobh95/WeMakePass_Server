package com.github.koobh95.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.koobh95.data.model.dto.PostDTO;
import com.github.koobh95.data.model.dto.response.PostPageResponse;
import com.github.koobh95.data.model.entity.Post;
import com.github.koobh95.data.model.entity.mapping.PostMapping;
import com.github.koobh95.data.repository.PostRepository;
import com.github.koobh95.service.PostService;

import lombok.RequiredArgsConstructor;

/**
 * 게시글과 관련된 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@Service("PostService")
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;
	
	private final int PAGE_SIZE = 20; // 한 페이지에 표시할 데이터 개수

	/**
	 * 특정 게시판의 게시글을 페이지 단위로 조회한다.
	 * 
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param pageNo 조회할 페이지 번호
	 */
	@Override
	public PostPageResponse postList(long boardNo, int pageNo) {
    	Page<PostMapping> page = postRepository.findByBoardNo(
    			PageRequest.of(pageNo, PAGE_SIZE,
    					Sort.by("postNo").descending()), 
    			boardNo);
    	return createPostPageResponse(page);
	}

	/**
	 * 특정 게시판, 특정 카테고리의 게시글을 페이지 단위로 조회한다.
	 * 
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param pageNo 조회할 페이지 번호
	 * @param category 조회할 카테고리
	 */
	@Override
	public PostPageResponse postListByCategory(long boardNo, int pageNo, String category) {
		Page<PostMapping> page = postRepository.findByBoardNoAndCategory(
				PageRequest.of(pageNo, PAGE_SIZE, 
						Sort.by("postNo").descending()),
				boardNo, category);
		return createPostPageResponse(page);
	}

	/**
	 *  DB에서 조회한 Page 객체를 기반으로 클라이언트에게 반환할 객체를 생성한다. 먼저 Page 객체에 있는
	 * Entity 리스트를 DTO 리스트로 변환한다. 그리고 페이지 번호와 마지막 페이지 여부를 
	 * PostPageResponse에 초기화 하여 반환한다.
	 * 
	 * @param page DB에서 Page로 조회한 게시글 목록
	 * @return
	 */
    private PostPageResponse createPostPageResponse(Page<PostMapping> page) {
    	Page<PostDTO> convertedPage = Post.toDtoList(page); 
		return new PostPageResponse(convertedPage.getContent(),
    			convertedPage.getNumber(),
    			convertedPage.isLast());
    }
}
