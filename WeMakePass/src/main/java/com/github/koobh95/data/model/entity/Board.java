package com.github.koobh95.data.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.ToString;

/**
 * 게시판 정보를 갖는 테이블 "board_tb"와 대응되는 Entity 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@Entity
@Table(name="board_tb")
@Getter
@ToString
public class Board {
	@Id
	@Column(name="board_no")
	private long boardNo; // 게시판의 고유 식별 코드
	@Column(name="board_name")
	private String boardName; // 게시판 이름
}
