package com.github.koobh95.exception;

import com.github.koobh95.base.BaseException;
import com.github.koobh95.data.model.enums.ErrorCode;

import lombok.Getter;

/**
 * 암호화된 데이터를 주고 받는 과정에서 복호화 에러가 발생했을 때 던지는 Exception 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Getter
public class AesDecryptException extends BaseException {
	
	public AesDecryptException(ErrorCode errorCode) {
		super(errorCode);
	}

	@Override
	public String toString() {
		return "AesDecryptException [toString()=" + super.toString() + "]";
	}
}
