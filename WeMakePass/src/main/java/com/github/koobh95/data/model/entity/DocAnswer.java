package com.github.koobh95.data.model.entity;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.github.koobh95.data.model.dto.DocAnswerDTO;
import com.github.koobh95.data.model.entity.key.DocAnswerKey;

import lombok.Getter;

/**
 * - 특정 필기 시험, 특정 문항의 답안 데이터를 갖는 테이블 "doc_answer_tb"와 대응되는 Entity 클래스.
 * - 복합키를 사용한다.
 * 
 * @author BH-Ku
 * @since 2024-01-06
 */
@Entity
@Table(name="doc_answer_tb")
@IdClass(DocAnswerKey.class)
@Getter
public class DocAnswer {
	@Id
	@Column(name="exam_id")
	private long examId; // 시험의 고유 식별 번호 
	@Id
	@Column(name="question_id")
	private long questionId; // 문항의 고유 식별 번호
	private int answer; // 문제의 답
	private String explanation; // 해답
	
	// DocAnswerEntity 리스트를 DocAnswerDTO 리스트로 변환하여 반환
	public static List<DocAnswerDTO> toDtoList(List<DocAnswer> entityList) {
		return entityList.stream()
				.map(e -> new DocAnswerDTO(e.answer, e.explanation))
				.collect(Collectors.toList());
	}
}
