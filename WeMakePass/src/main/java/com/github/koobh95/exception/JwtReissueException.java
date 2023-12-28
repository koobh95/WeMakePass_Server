package com.github.koobh95.exception;

import com.github.koobh95.base.BaseException;
import com.github.koobh95.data.model.enums.ErrorCode;

/**
 * 토큰 재발급 과정에서 문제가 발생하는 경우 던지는 Exception 클래스
 * 
 * @author BH-Ku
 * @since 2023-12-28
 */
public class JwtReissueException extends BaseException {

	public JwtReissueException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	@Override
	public String toString() {
		return "JwtReissueException [" + super.toString() + "]";
	}
}
