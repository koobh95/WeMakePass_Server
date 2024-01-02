package com.github.koobh95.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.koobh95.data.model.entity.ExamInfo;

/**
 * ExamInfo 클래스와 대응되는 Repository 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-01
 */
public interface ExamInfoRepository extends JpaRepository<ExamInfo, String> {

}
