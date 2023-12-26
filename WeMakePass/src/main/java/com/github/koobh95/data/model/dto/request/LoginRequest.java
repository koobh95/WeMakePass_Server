package com.github.koobh95.data.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * - 클라이언트 측에서 로그인을 시도할 때 아이디와 비밀번호를 서버로 전송하는 용도로 사용되는 DTO 클래스다.
 * - 데이터의 특성상 아이디와 비밀번호는 암호화된 채로 서버에 전달된다. 
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	private String userId; // 사용자 아이디
	private String password; // 비밀번호
}
