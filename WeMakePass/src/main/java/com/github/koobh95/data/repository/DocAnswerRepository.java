package com.github.koobh95.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.koobh95.data.model.entity.DocAnswer;

/**
 * DocAnswer 클래스와 대응되는 Repository 클래스
 * 
 * @author BH-Ku
 * @since 2024-01-06
 */
public interface DocAnswerRepository extends JpaRepository<DocAnswer, Long> {
	// 특정 시험의 답안 목록을 오름차순으로 조회
	List<DocAnswer> findByExamIdOrderByQuestionId(long examId);
}
