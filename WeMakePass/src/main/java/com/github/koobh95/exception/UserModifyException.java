package com.github.koobh95.exception;

import com.github.koobh95.base.BaseException;
import com.github.koobh95.data.model.enums.ErrorCode;

/**
 * 유저 정보를 변경하다가 문제가 발생할 경우 던지는 Exception 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-31
 */
public class UserModifyException extends BaseException {

	public UserModifyException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	@Override
	public String toString() {
		return "UserModifyException [" + super.toString() + "]";
	}
}
