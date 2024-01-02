package com.github.koobh95.data.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.koobh95.data.model.dto.JmDTO;

import lombok.Getter;

/**
 * 자격증 종목 정보를 갖는 테이블 "jm_tb"와 대응되는 Entity 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
@Entity
@Table(name="jm_tb")
@Getter
public class Jm {
	@Id
	@Column(name="jm_code")
	private String jmCode; // 종목의 고유 식별 번호
	@Column(name="jm_name")
	private String jmName; // 종목 이름
	@Column(name="qual_code")
	private String qualCode; // 종목 계열
	
	// 종목의 시험 리스트
	@OneToMany(mappedBy = "jm", fetch = FetchType.LAZY)
	private List<ExamInfo> examInfoList = new ArrayList<>(); 
	
	// JmEntity 리스트를 JmDTO 리스트로 변환
	public static List<JmDTO> toDtoList(List<Jm> entityList) {
		return entityList.stream()
				.map(e -> new JmDTO(e.getJmCode(), e.getJmName()))
				.collect(Collectors.toList());
	}
}
