package com.github.koobh95.data.model.entity;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.koobh95.data.model.dto.JmDTO;

import lombok.Getter;
import lombok.ToString;

/**
 * 테이블 'jm_tb'와 대응되는 Entity 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
@Entity
@Table(name="jm_tb")
@ToString
@Getter
public class JmEntity {
	@Id
	@Column(name="jm_code")
	private String jmCode; // 숫자 4개로 이루어진 종목 식별 코드
	@Column(name="jm_name")
	private String jmName; // 종목 이름
	@Column(name="qual_code")
	private String qualCode; // 종목 계열
	
	/**
	 * JmEntity 타입 리스트를 JmDTO 타입 리스트로 변환
	 * 
	 * @param entityList
	 * @return
	 */
	public static List<JmDTO> toDtoList(List<JmEntity> entityList) {
		return entityList.stream()
				.map(e -> new JmDTO(e.getJmCode(), e.getJmName()))
				.collect(Collectors.toList());
	}
}
