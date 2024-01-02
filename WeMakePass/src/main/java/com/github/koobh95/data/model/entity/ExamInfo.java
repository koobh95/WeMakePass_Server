package com.github.koobh95.data.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

/**
 * 특정 자격증의 시험 정보를 갖는 테이블 "exam_info_tb"와 대응되는 Entity 클래스.
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="jm_code", insertable = false, updatable = false)
	private Jm jm;
}
