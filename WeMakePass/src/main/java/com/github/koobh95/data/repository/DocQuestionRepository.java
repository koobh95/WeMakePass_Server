package com.github.koobh95.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.koobh95.data.model.entity.DocQuestion;
import com.github.koobh95.data.model.entity.mapping.SubjectNameMapping;

/**
 * DocQuestion 클래스와 대응되는 Repository 클래스
 * 
 * @author BH-Ku
 * @since 2024-01-06
 */
public interface DocQuestionRepository extends JpaRepository<DocQuestion, Long> {
	/**
	 *  필기 시험의 과목 목록을 조회하되 과목의 순서는 문항 번호의 오름차순 순서로 유지되어야 한다. 순서
	 * 유지를 이유로 DISTINCT는 사용할 수 없다. 정렬을 위해서는 questionId도 같이 조회해야 하는데 
	 * 문항 번호는 모두 다른 값을 가지기 때문에 같이 조회될 경우 DISTINCT를 사용하는 의미가 없어지기 
	 * 때문이다. 따라서 모든 데이터를 조회한 뒤 과목 이름(subjectName)만을 매핑하여 리스트화한다.
	 * 
	 * @param examId 조회할 시험의 고유 식별 번호
	 * @return
	 */
	List<SubjectNameMapping> findByExamIdOrderByQuestionId(long examId);
	
	/**
	 * 특정 시험의 문제 목록을 조회하되 문항 번호 오름차순으로 조회한다.
	 * 
	 * @param examId 조회할 시험의 고유 식별 번호
	 * @return
	 */
	List<DocQuestion> findByExamIdOrderByQuestionIdAsc(long examId);
}
