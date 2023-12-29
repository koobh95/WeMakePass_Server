package com.github.koobh95.data.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * - 클라이언트 측에서 회원가입을 시도할 때 회원가입에 필요한 필수 정보들을 서버로 전송하는 용도로 사용하는
 *  DTO 클래스다.
 * - 모든 데이터는 개인 정보이므로 암호화된 채로 서버로 전송된다.
 * 
 * @author BH-Ku
 * @since 2023-12-29
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
	private String userId; // 사용할 아이디
	private String password; // 사용할 비밀번호
	private String nickname; // 사용할 닉네임
	private String email; // 사용할 이메일
}
