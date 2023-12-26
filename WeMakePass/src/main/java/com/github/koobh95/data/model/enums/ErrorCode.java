package com.github.koobh95.data.model.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 클라이언트에게 반환할 에러 코드, 에러 메시지를 모아놓은 열거형 타입.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
	// AES256 암호화 실패.
	AES_ENCRYPTION_ERROR(HttpStatus.UNAUTHORIZED, "데이터 암호화 실패"),
	// AES256 복호화 실패.
	AES_DECRYPTION_ERROR(HttpStatus.UNAUTHORIZED, "데이터 복호화 실패"),
	
	// AccessToken 인증 실패, 토큰이 만료되었음.
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AccessToken이 만료되었습니다."),
    // AccessToken 인증 실패, 서버에서 발급한 토큰이 아니거나 정상적인 접근이 아님.
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "비정상적인 접근입니다.(인증 실패)"), 
	
	// DB에 아이디가 존재하지 않음.
	USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),
	// 비밀번호가 일치하지 않음.
	PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	// 이메일 인증이 완료되지 않은 사용자.
	UNCERT_USER(HttpStatus.BAD_REQUEST, "이메일 인증이 완료되지 않은 사용자입니다."),
	// 탈퇴된 사용자.
	WITHDRAW_ACCOUNT(HttpStatus.BAD_REQUEST, "탈퇴된 계정입니다.");
		
	private HttpStatus httpStatus; // 클라이언트에게 반환할 상태 코드
	private String message; // 클라이언트에게 반환할 메시지
}
