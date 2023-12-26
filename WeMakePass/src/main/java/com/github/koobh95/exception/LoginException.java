package com.github.koobh95.exception;

import com.github.koobh95.base.BaseException;
import com.github.koobh95.data.model.enums.ErrorCode;

/**
 * 로그인 과정에서 아이디 찾기 실패, 비밀번호 불일치 등의 문제가 발생할 경우 던지는 Exception 클래스. 
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
public class LoginException extends BaseException {

	public LoginException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	@Override
	public String toString() {
		return "LoginException [" + super.toString() + "]";
	}
}
