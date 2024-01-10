package com.github.koobh95.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.koobh95.annotation.LoginRequired;
import com.github.koobh95.data.model.dto.request.PostWriteRequest;
import com.github.koobh95.data.model.dto.response.PostPageResponse;
import com.github.koobh95.service.PostService;

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
	@PutMapping(value = "/write")
	public ResponseEntity<String> write(
			@RequestBody PostWriteRequest postWriteRequest) {
		postService.write(postWriteRequest);
		return ResponseEntity.ok("게시글을 저장했습니다.");
	}
}
