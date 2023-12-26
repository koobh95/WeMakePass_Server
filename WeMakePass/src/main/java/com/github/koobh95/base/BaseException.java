package com.github.koobh95.base;

import com.github.koobh95.data.model.enums.ErrorCode;

import lombok.Getter;

/**
 * - 코드 상에서 인위적으로 발생시키는 Custom Exception 클래스들이 공통적으로 상속받는 클래스다.
 * - 커스텀 Exception 클래스들은 두 생성자 중 하나를 선택적으로 구현한다는 것을 제외하면 모두 같은 
 *  구조를 가진다.  
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Getter
public class BaseException extends RuntimeException {
	protected ErrorCode errorCode; // 클라이언트에 반환할 에러 코드
	protected String errorMessage; // 로그에 출력할 메시지

	// 로그에 별도의 메시지를 출력하지 않고 단순 에러 코드만 출력하는 경우 사용하는 생성자.
	protected BaseException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		errorMessage = "";
	}
	
	// 로그에 에러 코드와 함께 부가적인 메시지를 출력할 때 사용하는 생성자.
	protected BaseException(ErrorCode errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "code=" + errorCode + ", reason=" + errorCode.getMessage() +
				", errorMessage=\"" + errorMessage + "\"]";
	}
}
