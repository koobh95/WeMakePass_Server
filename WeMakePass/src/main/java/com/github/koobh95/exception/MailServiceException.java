package com.github.koobh95.exception;

import com.github.koobh95.base.BaseException;
import com.github.koobh95.data.model.enums.ErrorCode;

/**
 * 메일 서비스를 처리하다가 문제가 발생할 경우 던지는 Exception 클래스. 
 * 
 * @author BH-Ku
 * @since 2023-12-29
 */
public class MailServiceException extends BaseException {

	public MailServiceException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	@Override
	public String toString() {
		return "MailServiceException [" + super.toString() + "]";
	}
}
