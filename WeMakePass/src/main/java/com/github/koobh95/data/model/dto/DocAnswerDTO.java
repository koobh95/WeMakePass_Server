package com.github.koobh95.data.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DocAnswer Entity 클래스에서 네트워크 전송에 필요한 데이터만을 갖는 DTO 클래스
 * 
 * @author BH-Ku
 * @since 2024-01-06
 */
@AllArgsConstructor
@Getter
public class DocAnswerDTO {
	private int answer; // 답안 번호
	private String explanation; // 해설
}
