package com.github.koobh95.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.koobh95.data.model.entity.ExamInfo;

/**
 * ExamInfo 클래스와 대응되는 Repository 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-01
 */
public interface ExamInfoRepository extends JpaRepository<ExamInfo, String> {
	// 종목 코드, 시행 년도, 시행 회차, 시험 형식과 일치하는 데이터 조회
	ExamInfo findByJmCodeAndImplYearAndImplSeqAndExamFormat(
			String jmCode, int implYear, int implSeq, String examFormat);
	// 특정 종목 코드를 가지는 시험을 조회 
	List<ExamInfo> findByJmCode(String jmCode);
}
