package com.github.koobh95.service;

/**
 * 메일 관련 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2023-12-30
 */
public interface MailService {
	// 회원가입 후 계정 인증을 위해 가입 시 입력한 이메일로 인증 코드를 발송하는 메서드. 
	public void sendAccountCertMail(String userId);
	// 이메일로 전송된 
	public void confirm(String userId, String code);
}
