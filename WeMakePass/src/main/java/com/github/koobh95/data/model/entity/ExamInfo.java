package com.github.koobh95.data.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

/**
 * - 특정 자격증의 시험 정보를 갖는 테이블 "exam_info_tb"와 대응되는 Entity 클래스.
 * - 모든 데이터가 클라이언트에서 사용되므로 별도의 DTO 클래스를 생성하지 않고 이 클래스를 그대로 사용한다.
 * 
 * @author BH-Ku
 * @since 2024-01-01
 */
@Entity
@Table(name="exam_info_tb")
@Getter
public class ExamInfo {
	@Id
	@Column(name="exam_id")
	private long examId; // 데이터의 고유 식별 번호
	@Column(name="jm_code")
	private String jmCode; // 종목의 고유 식별 번호
	@Column(name="impl_year")
	private int implYear; // 시행 년도
	@Column(name="impl_seq")
	private int implSeq; // 시행 회차
	@Column(name="exam_format")
	private String examFormat; // 시험 형식(필기, 실기, 1차, 2차 등)
	@Column(name="num_of_question")
	private int numOfQuestion; // 시험의 총 문항 수
	@Column(name="time_limit")
	private int timeLimit; // 시험 제한 시간
	
	// 시험의 종목
	@JsonIgnore // DTO 클래스를 별도로 생성하지 않기 때문에 JSON에 이 데이터가 포함되지 않도록 함.
	@ManyToOne(fetch = FetchType.LAZY) // 상시 사용되지 않으므로 LAZY로 지정.
	@JoinColumn(name="jm_code", insertable = false, updatable = false)
	private Jm jm;
}
