package com.github.koobh95.exception;

import com.github.koobh95.base.BaseException;
import com.github.koobh95.data.model.enums.ErrorCode;

/**
 * 회원가입 과정에서 문제가 발생하는 경우 던지는 Exception 클래스
 * 
 * @author BH-Ku
 * @since 2023-12-29
 */
public class SignUpException extends BaseException{

	public SignUpException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	@Override
	public String toString() {
		return "SignUpException [" + super.toString() + ']';
	}
}
