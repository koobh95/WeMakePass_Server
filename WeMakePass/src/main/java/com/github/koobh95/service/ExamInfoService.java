package com.github.koobh95.service;

import java.util.List;

import com.github.koobh95.data.model.entity.ExamInfo;

/**
 * 시험 정보를 조회하는 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-03
 */
public interface ExamInfoService {
	// 종목 코드, 시행 년도, 시행 회차, 시험 형식과 일치하는 시험 정보를 조회
	ExamInfo getExamInfo(String jmCode, int implYear, int implSeq, 
			String examFormat);
	// 특정 종목의 시험을 조회
	List<ExamInfo> getExamInfoList(String jmCode);
}
