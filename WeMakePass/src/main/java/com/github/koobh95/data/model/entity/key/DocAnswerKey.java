package com.github.koobh95.data.model.entity.key;

import java.io.Serializable;

/**
 * DocAnswer Entity 클래스의 복합키 클래스
 * 
 * @author BH-Ku
 * @since 2024-01-06
 */
public class DocAnswerKey implements Serializable {
	private long examId; // 시험의 고유 식별 번호
	private long questionId; // 시험 문항의 고유 식별 번호
}
