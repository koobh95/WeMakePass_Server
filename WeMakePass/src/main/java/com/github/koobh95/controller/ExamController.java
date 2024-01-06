package com.github.koobh95.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.koobh95.annotation.LoginRequired;
import com.github.koobh95.data.model.dto.DocAnswerDTO;
import com.github.koobh95.data.model.dto.ExamResultDTO;
import com.github.koobh95.data.model.entity.DocQuestion;
import com.github.koobh95.service.ExamDocService;
import com.github.koobh95.service.ExamService;

import lombok.RequiredArgsConstructor;

/**
 * 시험 문제, 답안 등을 조회하는 API를 제공.
 * 
 * @author BH-Ku
 * @since 2024-01-06
 */
@RestController
@RequestMapping("api/exam")
@RequiredArgsConstructor
public class ExamController {
	private final ExamService examService;
	private final ExamDocService examDocService;
	
	/**
	 * 특정 필기 시험의 문제 목록을 조회한다.
	 * 
	 * @param examId 조회할 시험의 고유 식별 번호
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/doc/question")
	public List<DocQuestion> docQuestionList(@RequestParam long examId){
		return examDocService.getQuestionList(examId);
	}
	
	/**
	 * 특정 필기 시험의 답안 목록을 조회한다.
	 * 
	 * @param examId
	 * @return
	 */
	@LoginRequired
	@GetMapping(value = "/doc/answer")
	public List<DocAnswerDTO> docAnswerList(@RequestParam long examId){
		System.out.println("[Request] ExamController/docAnswerList");
		return examDocService.getAnswerList(examId);
	}

	/**
	 * 시험 응시 결과를 DB에 저장한다.
	 * 
	 * @param examResultDTO
	 * @return
	 */
	@LoginRequired
	@PutMapping(value = "/save")
	public ResponseEntity<String> saveExamResult(
			@RequestBody ExamResultDTO examResultDTO) {
		examService.saveResult(examResultDTO);
		return ResponseEntity.ok("시험 결과를 성공적으로 저장했습니다.");
	}
}
