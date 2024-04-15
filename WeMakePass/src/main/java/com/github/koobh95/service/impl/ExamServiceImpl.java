package com.github.koobh95.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.koobh95.data.model.dto.ExamResultDTO;
import com.github.koobh95.data.model.entity.ExamInfo;
import com.github.koobh95.data.model.entity.ExamResult;
import com.github.koobh95.data.repository.ExamInfoRepository;
import com.github.koobh95.data.repository.ExamResultRepository;
import com.github.koobh95.service.ExamService;

import lombok.RequiredArgsConstructor;

/**
 * 시험 정보를 조회하는 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-03
 */
@Service("examInfoService")
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
	private final ExamInfoRepository examInfoRepository;
	private final ExamResultRepository examResultRepository;
	
	/**
	 * 특정 종목의 시험을 조회
	 * 
	 * @param jmCode 조회할 종목 코드
	 */
	@Override
	public List<ExamInfo> getExamInfoList(String jmCode) {
		return examInfoRepository.findByJmCode(jmCode);
	}

	/**
	 * 시험 결과를 저장
	 * 
	 * @param examResultDTO 시험 결과와 관련된 데이터를 가진 객체
	 */
	@Override
	public void saveResult(ExamResultDTO examResultDTO) {
		examResultRepository.save(ExamResult.create(examResultDTO.getExamId(), 
				examResultDTO.getUserId(), 
				examResultDTO.getReasonForRejection(),
				examResultDTO.getElapsedTime(),
				examResultDTO.getScore(),
				examResultDTO.getAnswerSheet()));
	}
}
