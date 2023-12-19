package com.github.koobh95.service;

import java.util.List;

import com.github.koobh95.data.model.dto.JmDTO;

/**
 * 시험 종목 정보와 관련된 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
public interface JmService {
	// Keyword와 JmName이 부분일치하는 데이터 검색
	List<JmDTO> searchByJmName(String keyword);
}
