package com.github.koobh95.data.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 시험 결과를 저장하는 테이블 "exam_result_tb"와 대응되는 Entity 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@Entity
@Table(name="exam_result_tb")
@SequenceGenerator(
		name="EXAM_RESULT_SEQ_GENERATOR",
		sequenceName = "seq_examResult_examResultId",
		allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class ExamResult {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "EXAM_RESULT_SEQ_GENERATOR")
	@Column(name="exam_result_id")
	private long examResultId; // 고유 식별 번호, Sequence에 의해 생성
	@Column(name="exam_id")
	private long examId; // 시험의 고유 식별 번호
	@Column(name="user_id")
	private String userId; // 응시한 사용자의 ID
	@Column(name="exam_date")
	private LocalDateTime examDate; // 응시한 날짜
	@Column(name="reason_for_rejection")
	private String reasonForRejection; // 시험 불합격 시 불합격 사유
	@Column(name="elapsed_time")
	private long elapsedTime; // 경과 시간
	private int score; // 총점
	@Column(name="answer_sheet")
	private String answerSheet; // JSON 데이터 형식으로 이루어진 답안 리스트

	/**
	 *  DB에 추가할 새로운 ExamResult 객체를 생성할 때 사용되는 메서드다. 기본키에 해당하는 
	 * examResultId는 시퀀스로 자동 생성하고 시험 종료 시간은 데이터를 저장하는 시간으로 설정한다.
	 * 이 외에 데이터는 클라이언트로부터 받은 DTO 클래스에서 추출한다.
	 */
	public static ExamResult create(long examId, String userId, 
			String reasonForRejection, long elapsedTime, int score, 
			String answerSheet) {
		ExamResult e = new ExamResult();
		e.examId = examId;
		e.userId = userId;
		e.examDate = LocalDateTime.now();
		e.reasonForRejection = reasonForRejection;
		e.elapsedTime = elapsedTime;
		e.score = score;
		e.answerSheet = answerSheet;
		return e;
	}
}
