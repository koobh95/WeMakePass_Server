package com.github.koobh95.controller;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.koobh95.annotation.LoginRequired;
import com.github.koobh95.data.model.entity.ExamInfo;
import com.github.koobh95.service.ExamDocService;
import com.github.koobh95.service.ExamService;

import lombok.RequiredArgsConstructor;

/**
 * 시험 정보 관련 데이터를 조회하는 API를 제공.
 * 
 * @author BH-Ku
 * @since 2024-01-03
 */
@RestController
@RequestMapping("api/exam-info")
@RequiredArgsConstructor
public class ExamInfoController {
	private final ExamService examService;
	private final ExamDocService examDocService;
		
	/**
	 * 특정 종목의 시험을 조회한다.
	 * 
	 * @param jmCode 조회할 종목의 식별 번호
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/jm")
	public List<ExamInfo> examInfoList(@RequestParam String jmCode) {
		return examService.getExamInfoList(jmCode);
	}

	/**
	 * 특정 종목의 과목 목록을 조회한다.
	 * @param examId 조회할 시험의 고유 식별 번호
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/subject")
	public Set<String> docSubjectList(@RequestParam long examId) {
		return examDocService.getSubjectList(examId);
	}
}
