package com.github.koobh95.exception;

import com.github.koobh95.base.BaseException;
import com.github.koobh95.data.model.enums.ErrorCode;

/**
 * 게시글과 관련된 비지니스 로직을 처리하다가 예외가 발생할 경우 던지는 Exception 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-10
 */
public class PostException extends BaseException {

	public PostException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	@Override
	public String toString() {
		return "PostException [" + super.toString() + "]";
	}
}
