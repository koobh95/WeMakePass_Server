package com.github.koobh95.data.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Jm Entity 클래스에서 네트워크 전송에 필요한 데이터만을 갖는 DTO 클래스
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
@AllArgsConstructor
@Getter
public class JmDTO {
	private final String jmCode; // 숫자 4개로 이루어진 종목 식별 코드
	private final String jmName; // 종목 이름
}
