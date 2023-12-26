package com.github.koobh95.data.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자가 로그인에 성공했을 때 클라이언트(어플리케이션)에서 저장해야 할 데이터들을 가지는 DTO 클래스다.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Getter
@AllArgsConstructor
public class UserInfoDTO {
	private final String userId; // 유저 아이디
	private final String nickname; // 닉네임
	private final String email; // 인증 이메일
}
