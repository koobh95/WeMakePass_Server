package com.github.koobh95.data.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.ToString;

/**
 * - 필기 시험의 문항 1개에 대한 정보를 갖는 테이블 "doc_question_tb"와 대응되는 Entity 클래스.
 * - 모든 데이터가 클라이언트에서 사용되므로 별도의 DTO 클래스를 생성하지 않고 이 클래스를 그대로 사용한다.
 * 
 * @author BH-Ku
 * @since 2024-01-06
 */
@Entity
@Table(name="doc_question_tb")
@Getter
@ToString
public class DocQuestion {
	@Id
	@Column(name="question_id")
	private long questionId; // 문항의 고유 식별 번호
	@Column(name="exam_id")
	private long examId; // 문항이 속한 시험의 고유 식별 번호
	@Column(name="subject_name")
	private String subjectName; // 과목 이름
	private String question; // 질의
	private String options; // JSON 형태로 저장된 객관식 데이터
	private int score; // 배점
	@Column(name="ref_image")
	private String refImage; // 참조 이미지가 있을 경우 파일 이름
}
