package com.github.koobh95.exception;

import com.github.koobh95.base.BaseException;
import com.github.koobh95.data.model.enums.ErrorCode;

/**
 *  보안이 적용된 URI에 접근하려는 사용자의 계정 상태를 확인하는 AccountValidationInterceptor에서
 * 계정 검증에 실패했을 때 던지는 Exception 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-28
 */
public class AccountValidationException extends BaseException {

	public AccountValidationException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	@Override
	public String toString() {
		return "AccountValidationException [" + super.toString() + "]";
	}	
}
