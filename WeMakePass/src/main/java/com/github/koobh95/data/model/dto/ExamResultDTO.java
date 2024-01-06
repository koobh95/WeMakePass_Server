package com.github.koobh95.data.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ExamResult Entity 클래스에서 네트워크 전송에 필요한 데이터만을 갖는 DTO 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@Getter
@NoArgsConstructor
public class ExamResultDTO {
	private long examId; // 시험의 고유 식별 번호 
	private String userId; // 시험을 응시한 사용자의 ID
	private String reasonForRejection; // 불합격한 경우 불합격 사유
	private long elapsedTime; // 경과 시간
	private int score; // 총점
	private String answerSheet; // JSON 데이터 형식으로 이루어진 답안 리스트 
}
