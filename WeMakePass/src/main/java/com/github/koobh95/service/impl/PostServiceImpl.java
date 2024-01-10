package com.github.koobh95.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.koobh95.data.model.dto.PostDTO;
import com.github.koobh95.data.model.dto.PostDetailDTO;
import com.github.koobh95.data.model.dto.request.PostWriteRequest;
import com.github.koobh95.data.model.dto.response.PostPageResponse;
import com.github.koobh95.data.model.entity.Post;
import com.github.koobh95.data.model.entity.mapping.PostMapping;
import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.data.repository.PostRepository;
import com.github.koobh95.exception.PostException;
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
	public PostPageResponse postListByCategory(long boardNo, int pageNo, 
			String category) {
		Page<PostMapping> page = postRepository.findByBoardNoAndCategory(
				PageRequest.of(pageNo, PAGE_SIZE, 
						Sort.by("postNo").descending()),
				boardNo, category);
		return createPostPageResponse(page);
	}

    /**
     * 새로운 게시글을 DB에 저장한다.
     * 
     * @param postWriteRequest 새로운 게시글에 대한 데이터를 갖고 있는 객체
     */
	@Override
	public void write(PostWriteRequest postWriteRequest) {
		postRepository.save(postWriteRequest.toEntity());
	}

	/**
	 * - 특정 게시물을 조회했을 경우 클라이언트에 표시할 데이터를 반환한다.
	 * - 조회하려는 게시물이 삭제된 상태일 가능성이 있으므로 삭제 여부를 확인한다.
	 * - 게시글을 조회하는 시점에서 조회수를 1 증가시킨 뒤 Entity 객체를 DTO 객체로 변환, 반환한다.
	 * 
	 * @param postNo 조회할 게시글의 고유 식별 번호
	 */
	@Transactional
	@Override
	public PostDetailDTO postDetail(long postNo) {
		Post entity = postRepository.findByPostNo(postNo);
		
		if(entity.getDeleteDate() != null)
			throw new PostException(ErrorCode.POST_LOADING_FAILED_POST_DELETED,
					"postNo=" + postNo);
		
		entity.increaseHit();
		return new PostDetailDTO(
				entity.getCategory(), 
				entity.getUser().getNickname(),
				entity.getTitle(),
				entity.getContent(),
				entity.getRegDate(),
				entity.getHit());
	}
	
	/**
	 * 특정 게시판에서 게시글 제목과 키워드가 부분 일치하는 게시글을 페이지 단위로 조회한다.
	 * 
	 * @param pageNo 조회할 페이지 번호
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param keyword 검색어
	 */
	@Override
	public PostPageResponse searchTitle(int pageNo, long boardNo, 
			String keyword) {
    	Page<PostMapping> page = postRepository.searchByTitle(
    			PageRequest.of(pageNo, PAGE_SIZE, Sort.by("postNo").descending()),
    			boardNo, keyword);
		return createPostPageResponse(page);
	}
	
	/**
	 * 특정 게시판에서 게시글의 내용에서 키워드가 부분 일치하는 게시글을 페이지 단위로 조회한다.
	 * 
	 * @param pageNo 조회할 페이지 번호
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param keyword 검색어
	 */
	@Override
	public PostPageResponse searchContent(int pageNo, long boardNo, 
			String keyword) {
    	Page<PostMapping> page = postRepository.searchByContent(
    			PageRequest.of(pageNo, PAGE_SIZE, Sort.by("postNo").descending()),
    			boardNo, keyword);
		return createPostPageResponse(page);
	}
	
	/**
	 * 특정 게시판에서 게시글의 제목 혹은 내용과 키워드가 부분 일치하는 게시글을 페이지 단위로 조회한다.
	 * 
	 * @param pageNo 조회할 페이지 번호
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param keyword 검색어
	 */
	@Override
	public PostPageResponse searchTitleAndContent(int pageNo, long boardNo, 
			String keyword) {
    	Page<PostMapping> page = postRepository.searchByTitleAndContent(
    			PageRequest.of(pageNo, PAGE_SIZE, Sort.by("postNo").descending()),
    			boardNo, keyword);
		return createPostPageResponse(page);
	}
	
	/**
	 *  특정 게시판에서 게시글 제목과 키워드가 부분 일치하면서 특정 카테고리에 해당하는 게시글을 페이지 
	 * 단위로 조회한다.
	 * 
	 * @param pageNo 조회할 페이지 번호
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param category 조회할 카테고리
	 * @param keyword 검색어
	 */
	@Override
	public PostPageResponse searchCategoryAndTitle(int pageNo, long boardNo,
			String category, String keyword) {
    	Page<PostMapping> page = postRepository.searchByCategoryAndTitle(
    			PageRequest.of(pageNo, PAGE_SIZE, 
    					Sort.by("postNo").descending()),
    			boardNo, category, keyword);
		return createPostPageResponse(page);
	}

	/**
	 *  특정 게시판에서 게시글의 내용과 키워드가 부분 일치하면서 특정 카테고리에 해당하는 게시글을 페이지
	 * 단위로 조회한다.
	 * 
	 * @param pageNo 조회할 페이지 번호
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param category 조회할 카테고리
	 * @param keyword 검색어
	 */
	@Override
	public PostPageResponse searchCategoryAndContent(int pageNo, long boardNo, 
			String category, String keyword) {
    	Page<PostMapping> page = postRepository.searchByCategoryAndContent(
    			PageRequest.of(pageNo, PAGE_SIZE, Sort.by("postNo").descending()),
    			boardNo, category, keyword);
		return createPostPageResponse(page);
	}

	/**
	 *  특정 게시판에서 게시글 제목 혹은 내용과 키워드가 부분 일치하면서 특정 카테고리에 해당하는 게시글을
	 * 페이지 단위로 조회한다.
	 * 
	 * @param pageNo 조회할 페이지 번호
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param category 조회할 카테고리
	 * @param keyword 검색어
	 */
	@Override
	public PostPageResponse searchCategoryAndTitleAndContent(int pageNo, 
			long boardNo, String category, String keyword) {
    	Page<PostMapping> page = postRepository.searchByCategoryAndTitleAndContent(
    			PageRequest.of(pageNo, PAGE_SIZE, Sort.by("postNo").descending()),
    			boardNo, category, keyword);
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
