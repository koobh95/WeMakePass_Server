package com.github.koobh95.data.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  클라이언트 측에서 비밀번호 변경을 시도할 때 아이디와 암호화된 비밀번호를 전송하는 용도로 사용하는 DTO 
 * 클래스. 
 * 
 * @author BH-Ku
 * @since 2023-12-31
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PasswordResetRequest {
	private String userId; // 아이디
	private String password; // 암호화된 비밀번호
}
