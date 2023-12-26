package com.github.koobh95.data.model.dto.response;

import com.github.koobh95.data.model.enums.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * - 클라이언트의 요청을 처리할 수 없는 상황에 마주했을 때 클라이언트 측이 해결할 수 있도록 에러 코드와
 *  에러 메시지를 전달하기 위해서 사용되는 DTO 클래스다.
 * - 이 클래스는 반환되는 Response의 Body에 쓰여진다.
 * - 이 클래스는 이 프로젝트에서 사용 중인 열거형 타입인 ErrorCode를 기반으로만 작성된다.
 *  code는 ErrorCode의 이름이 그대로 사용되고, message는 ErrorCode가 가진 메시지를 그대로 
 *  사용한다.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
	private final String code; // String으로 이루어진 에러 코드
	private final String message; // 클라이언트에 반환할 에러 메시지
	
	// 열거형 타입 ErrorCode를 기반으로 ErrorResponse를 객체화하여 반환한다. 
	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode.toString(), errorCode.getMessage());
	}
}
