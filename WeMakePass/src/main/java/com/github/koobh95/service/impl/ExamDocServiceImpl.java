package com.github.koobh95.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.github.koobh95.data.model.entity.mapping.SubjectNameMapping;
import com.github.koobh95.data.repository.DocQuestionRepository;
import com.github.koobh95.service.ExamDocService;

import lombok.RequiredArgsConstructor;

/**
 * 필기 시험 조회 관련된 비지니스 로직를 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-06
 */
@Service("examDocService")
@RequiredArgsConstructor
public class ExamDocServiceImpl implements ExamDocService{
	private final DocQuestionRepository docQuestionRepository;
	
	/**
	 * - 특정 시험의 과목 목록을 반환한다.
	 * - 조회하려는 시험의 식별 번호를 조회하되 문항 번호를 내림차순으로 조회하여 각 문항의 과목 순서를
	 *  유지하여 조회할 수 있게 한다. 읽어 온 데이터는 Set에 모두 저장하여 중복을 제거한 뒤 반환하는데
	 *  이 때 Set에 저장되는 과목의 순서를 보장하기 위해서 HashSet이 아닌 LinkedHashSet을 
	 *  사용하였다.
	 * - 메모리 낭비를 줄이기 위해서 과목명만 읽어올 수 있도록 Mapping 클래스를 사용하였다. 
	 *  
	 * 단순히 subjectName에 DISTINCT를 
	 * 사용하여 데이터를 읽을 경우 questionId가 없어 정렬이 불가능하기 때문에
	 * 모든 데이터를  
	 */
	@Override
	public Set<String> getSubjectList(long examId) {
		List<SubjectNameMapping> subjectList = docQuestionRepository.
				findByExamIdOrderByQuestionId(examId);
		Set<String> subjectSet = new LinkedHashSet<>();
		
		for(SubjectNameMapping m : subjectList)
			subjectSet.add(m.getSubjectName());
		
		return subjectSet;
	}
}
