package com.github.koobh95.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.koobh95.data.model.entity.ExamResult;

/**
 * ExamResult 클래스와 대응되는 Repository 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

}
