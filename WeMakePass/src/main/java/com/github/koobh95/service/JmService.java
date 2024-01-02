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
	// Keyword로 종목 이름을 검색하되, DB에 시험 데이터를 가진 종목에 한하여 검색
	List<JmDTO> searchByJmNameWithExam(String keyword);
}
