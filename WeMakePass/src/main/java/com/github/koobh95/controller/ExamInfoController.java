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
@RequestMapping("api/exam_info")
@RequiredArgsConstructor
public class ExamInfoController {
	private final ExamService examInfoService;
	private final ExamDocService examDocService;
	
	/**
	 * 파라미터로 받은 종목 코드, 시행 년도, 시행 회차, 시험 형식과 일치하는 시험 데이터를 조회  
	 * 
	 * @param jmCode 종목 식별 번호
	 * @param implYear 시행 년도
	 * @param implSeq 시행 회차
	 * @param examFormat 시험 형식
	 */
	@LoginRequired
	@GetMapping
	public ExamInfo examInfo(
			@RequestParam String jmCode, 
			@RequestParam int implYear, 
			@RequestParam int implSeq,
			@RequestParam String examFormat) {
		return examInfoService
				.getExamInfo(jmCode, implYear, implSeq, examFormat);
	}
	
	/**
	 * 특정 종목의 시험을 조회한다.
	 * 
	 * @param jmCode 조회할 종목의 식별 번호
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/list")
	public List<ExamInfo> examInfoList(@RequestParam String jmCode) {
		return examInfoService.getExamInfoList(jmCode);
	}

	/**
	 * 특정 종목의 과목 목록을 조회한다.
	 * @param examId 조회할 시험의 고유 식별 번호
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/subject_list")
	public Set<String> docSubjectList(@RequestParam long examId) {
		return examDocService.getSubjectList(examId);
	}
}
