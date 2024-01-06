package com.github.koobh95.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.koobh95.data.model.entity.ExamInfo;
import com.github.koobh95.data.repository.ExamInfoRepository;
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

	/**
	 * 종목 코드, 시행 년도, 시행 회차, 시험 형식과 일치하는 시험 정보를 조회
	 * 
	 * @param jmCode 종목 코드
	 * @param implYear 시행 년도
	 * @param implSeq 시행 회차
	 * @param examFormat 시험 형식
	 */
	@Override
	public ExamInfo getExamInfo(String jmCode, int implYear, int implSeq,
			String examFormat) {
		return examInfoRepository.
				findByJmCodeAndImplYearAndImplSeqAndExamFormat(
						jmCode, implYear, implSeq, examFormat);
	}

	/**
	 * 특정 종목의 시험을 조회
	 * 
	 * @param jmCode 조회할 종목 코드
	 */
	@Override
	public List<ExamInfo> getExamInfoList(String jmCode) {
		return examInfoRepository.findByJmCode(jmCode);
	}
}
