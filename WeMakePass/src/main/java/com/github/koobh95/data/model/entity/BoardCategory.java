package com.github.koobh95.data.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

/**
 * - 게시판 카테고리 정보를 갖는 테이블 "board_category_tb"와 대응되는 Entity 클래스.
 * - 모든 데이터가 클라이언트에서 사용되므로 별도의 DTO 클래스를 생성하지 않고 이 클래스를 그대로 사용한다.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@Entity
@Table(name="board_category_tb")
@Getter
public class BoardCategory {
	@Id
	@Column(name="board_category_no")
	private long boardCategoryNo; // 카테고리의 고유 식별 번호
	@Column(name="board_no")
	private long boardNo; // 카테고리가 속한 게시판의 고유 식별 번호
	@Column(name="category_name")
	private String categoryName; // 카테고리 이름
}
