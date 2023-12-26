package com.github.koobh95.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.koobh95.data.model.entity.Jm;

/**
 * Jm 클래스와 대응되는 Repository 클래스
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
public interface JmRepository extends JpaRepository<Jm, String>{
	/**
	 * JmName과 특정 문자열이 일치하는 데이터들을 검색하여 반환한다.
	 * 
	 * @param jmName 검색할 문자열
	 * @return
	 */
	List<Jm> findByJmNameContaining(String jmName);
}
