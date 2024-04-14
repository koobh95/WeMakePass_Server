package com.github.koobh95.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.koobh95.annotation.LoginRequired;
import com.github.koobh95.data.model.dto.PostDetailDTO;
import com.github.koobh95.data.model.dto.request.PostWriteRequest;
import com.github.koobh95.data.model.dto.response.PostPageResponse;
import com.github.koobh95.service.PostService;
import com.github.koobh95.util.StringUtil;

import lombok.RequiredArgsConstructor;

/**
 * 게시글 조회, 작성 등의 API를 제공.
 * 
 * @author BH-Ku
 * @since 2024-01-08
 */
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	
	/**
	 * 특정 게시판의 게시글을 페이지 단위로 조회
	 * 
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param pageNo 조회할 페이지 번호
	 * @return
	 */
	@LoginRequired
	@GetMapping
	public PostPageResponse postList(
			@RequestParam long boardNo,
			@RequestParam int pageNo) {
		return postService.postList(boardNo, pageNo);
	}

	/**
	 * 특정 게시판의 게시글, 특정 카테고리의 게시글을 페이지 단위로 조회
	 * 
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param pageNo 조회할 페이지 번호
	 * @param category 조회할 카테고리
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/category")
	public PostPageResponse postListByCategory(
			@RequestParam long boardNo,
			@RequestParam int pageNo, 
			@RequestParam String category) {
		return postService.postListByCategory(boardNo, pageNo, category);
	}
	
	/**
	 * 새로운 게시글을 DB에 추가.
	 * 
	 * @param postWriteRequest 작성된 게시글, 작성된 사용자 등에 대한 데이터를 가진 객체
	 * @return
	 */
	@LoginRequired
	@PostMapping(value = "/write")
	public ResponseEntity<String> write(
			@RequestBody PostWriteRequest postWriteRequest) {
		postService.write(postWriteRequest);
		return ResponseEntity.ok("게시글을 저장했습니다.");
	}
	
	/**
	 * 특정 게시글 조회 
	 * 
	 * @param postNo 조회할 게시글의 고유 식별 번호
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/detail")
	public PostDetailDTO postDetail(
			@RequestParam long postNo) {
		return postService.postDetail(postNo);
	}
	
	/**
	 * - 특정 게시판에서 게시글 제목과 키워드가 부분 일치하는 게시글을 조회
	 * - Category가 Null일 경우 모든 카테고리를 대상으로 조회하고 Null이 아닌 경우 특정 카테고리를
	 *  게시글을 조회한다.
	 * 
	 * @param pageNo 조회할 페이지 번호
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param category 조회할 카테고리
	 * @param keyword 검색어
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/search/title")
	public PostPageResponse searchTitle(
			@RequestParam int pageNo,
			@RequestParam long boardNo,
			@RequestParam String category,
			@RequestParam String keyword) {
		if(StringUtil.isEmpty(category))
			return postService.searchTitle(pageNo, boardNo, keyword);
		return postService.searchCategoryAndTitle(pageNo, boardNo, category,
				keyword);
	}
	
	/**
	 * - 특정 게시판에서 게시글의 내용에서 키워드가 부분 일치하는 게시글을 조회
	 * - Category가 Null일 경우 모든 카테고리를 대상으로 조회하고 Null이 아닌 경우 특정 카테고리를
	 *  게시글을 조회한다.
	 * 
	 * @param pageNo 조회할 페이지 번호
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param category 조회할 카테고리
	 * @param keyword 검색어
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/search/content")
	public PostPageResponse searchContent(
			@RequestParam int pageNo,
			@RequestParam long boardNo,
			@RequestParam String category,
			@RequestParam String keyword) {
		if(StringUtil.isEmpty(category))
			return postService.searchContent(pageNo, boardNo, keyword);
		return postService.searchCategoryAndContent(pageNo, boardNo, category,
				keyword);
	}
	
	/**
	 * - 특정 게시판에서 게시글의 제목 혹은 내용과 키워드가 부분 일치하는 게시글을 조회
	 * - Category가 Null일 경우 모든 카테고리를 대상으로 조회하고 Null이 아닌 경우 특정 카테고리를
	 *  게시글을 조회한다.
	 * 
	 * @param pageNo 조회할 페이지 번호
	 * @param boardNo 조회할 게시판의 고유 식별 번호
	 * @param category 조회할 카테고리
	 * @param keyword 검색어
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/search/title_and_content")
	public PostPageResponse searchByTitleAndContent(
			@RequestParam int pageNo,
			@RequestParam long boardNo,
			@RequestParam String category,
			@RequestParam String keyword) {
		if(StringUtil.isEmpty(category))
			return postService.searchTitleAndContent(pageNo, boardNo, keyword);
		return postService.searchCategoryAndTitleAndContent(pageNo, boardNo, 
				category, keyword);
	}
}
