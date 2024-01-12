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
    // RefreshToken 만료 혹은 모종의 사유로 재발급 실패.
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,
    		"로그인 정보가 만료되었습니다. 다시 로그인해주세요."),
	
	// DB에 아이디가 존재하지 않음.
	USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),
	// 비밀번호가 일치하지 않음.
	PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	// 이메일 인증이 완료되지 않은 사용자.
	UNCERT_USER(HttpStatus.BAD_REQUEST, "이메일 인증이 완료되지 않은 사용자입니다."),
	// 탈퇴된 사용자.
	WITHDRAW_ACCOUNT(HttpStatus.BAD_REQUEST, "탈퇴된 계정입니다."),
	
	// 회원가입 시 사용자가 입력한 아이디가 이미 DB에 존재함.
	USER_ID_DUPLICATED(HttpStatus.BAD_REQUEST, "이 아이디는 이미 사용 중입니다."),
	// 회원가입 시 사용자가 입력한 닉네임이 이미 DB에 존재함.
	NICKNAME_DUPLICATED(HttpStatus.BAD_REQUEST, "이 닉네임은 이미 사용 중입니다."),
	// 회원가입 시 사용자가 입력한 이메일이 이미 DB에 존재함.
	EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "이 메일은 이미 사용중입니다."),

	// 이메일 인증 검증 - 코드 불일치
	CERT_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "인증 번호가 일치하지 않습니다."),
	// 이메일 인증을 시도했으나 이메일이 없음
	EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
	
	// 비밀번호 변경 실패 - 변경하려는 비밀번호가 현재 사용 중인 비밀번호와 같음.
	PASSWORD_CURRENTLY_USE(HttpStatus.BAD_REQUEST, "이미 사용 중인 비밀번호입니다."),
	// 비밀번호 변경 실패 - 변경하려는 비밀번호가 이전에 사용한 비밀번호가 같음.
	PASSWORD_PREVIOUSLY_USE(HttpStatus.BAD_REQUEST,
			"전에 사용한 비밀번호는 다시 사용할 수 없습니다."),

	// 특정 게시글을 조회하려 했으나 게시물이 삭제된 상태.
	POST_LOADING_FAILED_POST_DELETED(HttpStatus.NOT_FOUND, "삭제된 게시글입니다."),
	
	// 댓글을 로딩하려 했으나 게시글이 삭제되어 실패.
	REPLY_LOADING_FAILED_POST_DELETED(HttpStatus.NOT_FOUND, 
			"해당 게시글은 삭제되었습니다."),
	
	// 댓글을 작성하려 했으나 게시글이 삭제되어 실패
	REPLY_WRITE_FAILED_POST_DELETED(HttpStatus.NOT_FOUND, 
			"해당 게시글은 삭제되었습니다."),
	// 답글을 작성하려 했으나 상위 댓글이 삭제된 상태
	REPLY_WRITE_FAILED_PARENT_REPLY_DELETED(HttpStatus.NOT_FOUND, 
			"댓글이 삭제되었습니다."),
	
	// 답글을 삭제하려 했으나 이미 게시글이 삭제된 상태
	REPLY_DELETE_FAILED_POST_DELETED(HttpStatus.NOT_FOUND, 
			"해당 게시글은 삭제되었습니다.");
	
	private HttpStatus httpStatus; // 클라이언트에게 반환할 상태 코드
	private String message; // 클라이언트에게 반환할 메시지
}
