package com.github.koobh95.service;

import java.util.List;

import com.github.koobh95.data.model.dto.ExamResultDTO;
import com.github.koobh95.data.model.entity.ExamInfo;

/**
 * 시험 정보를 조회하는 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-03
 */
public interface ExamService {
	// 특정 종목의 시험을 조회
	List<ExamInfo> getExamInfoList(String jmCode);
	// 시험 결과 저장
	void saveResult(ExamResultDTO examResultDTO);
}
