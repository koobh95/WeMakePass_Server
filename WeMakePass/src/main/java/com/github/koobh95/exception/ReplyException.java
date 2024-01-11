package com.github.koobh95.exception;

import com.github.koobh95.base.BaseException;
import com.github.koobh95.data.model.enums.ErrorCode;

/**
 * 댓글과 관련된 비지니스 로직을 처리하다가 예외가 발생할 경우 던지는 Exception 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-11
 */
public class ReplyException extends BaseException {

	public ReplyException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	@Override
	public String toString() {
		return "ReplyException [" + super.toString() + "]";
	}
}
