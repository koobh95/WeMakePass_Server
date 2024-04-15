package com.github.koobh95.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.koobh95.service.MailService;

import lombok.RequiredArgsConstructor;

/**
 * 메일 관련 API를 제공한다.
 * 
 * @author BH-Ku
 * @since 2023-12-30
 */
@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {
	private final MailService mailService;

	/**
	 * 계정 인증 이메일을 발송한다. 
	 * 
	 * @param userId 인증 메일을 발송할 유저의 ID
	 * @return
	 */
	@GetMapping("/account-cert")
	public ResponseEntity<String> accountCert(String userId) {
		mailService.sendAccountCertMail(userId);
		return ResponseEntity.ok("메일을 성공적으로 전송했습니다.");
	}
	
	/**
	 * 이메일로 발송된 인증 코드를 검증한다.
	 * 
	 * @param userId 검증할 유저의 ID
	 * @param code 사용자가 입력한 코드
	 * @return
	 */
	@GetMapping("/confirm")
	public ResponseEntity<String> confirm(String userId, String code) {
		mailService.confirm(userId, code);
		return ResponseEntity.ok("인증에 성공했습니다.");
	}
	 
	/**
	 * 사용자의 이메일을 기반으로 아이디 찾기 메일을 전송한다.
	 * 
	 * @param email 사용자가 입력한 이메일 주소
	 * @return
	 */
	@GetMapping("/forget-id")
	public ResponseEntity<String> forgetId(String email) {
		mailService.sendFindIdMail(email);
		return ResponseEntity.ok("메일을 성공적으로 전송했습니다.");
	}
	
	/**
	 * 비밀번호 찾기 메일을 전송한다.
	 * 
	 * @param userId 사용자가 입력한 아이디
	 * @return
	 */
	@GetMapping("/forget-password")
	public ResponseEntity<String> forgetPassword(String userId) {
		mailService.sendFindPasswordMail(userId);
		return ResponseEntity.ok("메일을 성공적으로 전송했습니다.");
	}
}
