package com.github.koobh95.service;

import java.util.Set;

/**
 * 필기 시험 조회 관련된 비지니스 로직를 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-06
 */
public interface ExamDocService {
	// 과목 목록을 조회한다.
	Set<String> getSubjectList(long examId);
}
