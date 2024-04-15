package com.github.koobh95.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.koobh95.annotation.LoginRequired;
import com.github.koobh95.data.model.dto.JmDTO;
import com.github.koobh95.service.JmService;

import lombok.RequiredArgsConstructor;

/**
 * 자격증 종목을 조회하는 API를 제공.
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/api/jm")
@RequiredArgsConstructor
public class JmController {
	private final JmService jmService;
	
	/**
	 * 종목 이름을 기준으로 데이터 조회
	 * 
	 * @param keyword 검색어
	 * @return JmName과 keyword가 부분 일치하는 데이터 리스트.
	 */
	@LoginRequired
	@GetMapping
	public List<JmDTO> search(@RequestParam String keyword) {
		return jmService.searchByJmName(keyword);
	}
	
	/**
	 * 종목 이름을 기준으로 데이터를 조회하되 시험 데이터가 있는 종목을 조회
	 * 
	 * @param keyword 검색어
	 * @return 시험 데이터가 있는 종목 리스트
	 */
	@LoginRequired
	@GetMapping(value = "/with-exam")
	public List<JmDTO> searchForJmWithExamInfo(@RequestParam String keyword){
		return jmService.searchByJmNameWithExam(keyword);
	}
}
