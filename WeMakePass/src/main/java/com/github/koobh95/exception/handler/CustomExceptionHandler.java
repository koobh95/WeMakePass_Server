package com.github.koobh95.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.koobh95.data.model.dto.response.ErrorResponse;
import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.exception.AccountValidationException;
import com.github.koobh95.exception.AesDecryptException;
import com.github.koobh95.exception.AesEncryptException;
import com.github.koobh95.exception.JwtReissueException;
import com.github.koobh95.exception.LoginException;
import com.github.koobh95.exception.MailServiceException;
import com.github.koobh95.exception.SignUpException;
import com.github.koobh95.exception.UserModifyException;

/**
 *  Controller, Service 단에서 요청을 처리할 수 없을 때 인위적으로 발생시키는 Exception을 
 * 캐치하는 ExceptionHandler 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@RestControllerAdvice
public class CustomExceptionHandler {
	
	// 암호화 실패
	@ExceptionHandler(AesEncryptException.class)
	protected ResponseEntity<ErrorResponse> aesEncryptionException(
			AesEncryptException e) {
		return createErrorResponseEntity(e.getErrorCode());
	}
	
	// 복호화 실패
	@ExceptionHandler(AesDecryptException.class)
	protected ResponseEntity<ErrorResponse> aesDecryptionException(
			AesDecryptException e) {
		return createErrorResponseEntity(e.getErrorCode());
	}
	
	// Interceptor에서 계정 검증 실패
	@ExceptionHandler(AccountValidationException.class)
	protected ResponseEntity<ErrorResponse> accountValidationException(
			AccountValidationException e) {
		return createErrorResponseEntity(e.getErrorCode());
	}
	
	// 토큰 재발급 과정에서 문제 발생
	@ExceptionHandler(JwtReissueException.class)
	protected ResponseEntity<ErrorResponse> jwtReissueException(
			JwtReissueException e) {
		return createErrorResponseEntity(e.getErrorCode());
	}
	
	// 로그인 실패
	@ExceptionHandler(LoginException.class)
	protected ResponseEntity<ErrorResponse> aesDecryptionException(
			LoginException e) {
		return createErrorResponseEntity(e.getErrorCode());
	}
	
	// 회원가입 실패
	@ExceptionHandler(SignUpException.class)
	protected ResponseEntity<ErrorResponse> signUpException(
			SignUpException e) {
		return createErrorResponseEntity(e.getErrorCode());
	}
	
	// 이메일 서비스 처리 중 에러 발생
	@ExceptionHandler(MailServiceException.class)
	protected ResponseEntity<ErrorResponse> mailServiceException(
			MailServiceException e) {
		return createErrorResponseEntity(e.getErrorCode());
	}
	
	// 사용자 정보 수정 실패 
	@ExceptionHandler(UserModifyException.class)
	protected ResponseEntity<ErrorResponse> userModifyException(
			UserModifyException e) {
		return createErrorResponseEntity(e.getErrorCode());
	}
	
	// 파라미터로 받은 ErrorCode에 따른 ErrorResponse를 생성, ResponseEntity에 세팅하여 반환.
	private ResponseEntity<ErrorResponse> createErrorResponseEntity (
			ErrorCode errorCode) {
		return ResponseEntity.status(errorCode.getHttpStatus())
				.body(ErrorResponse.of(errorCode));
	}
}
