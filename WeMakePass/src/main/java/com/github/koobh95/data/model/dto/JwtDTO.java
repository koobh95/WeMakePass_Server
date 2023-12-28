package com.github.koobh95.data.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * - 사용자가 로그인에 성공했을 때 혹은 토큰 재발급에 성공했을 때 AccessToken, RefreshToken을
 * 반환하는데 사용되는 DTO 클래스.
 * - RefreshToken은 클라이언트로 전송할 때 암호화된 채로 전송되고 클라이언트로부터 전송받을 때
 * 복호화된 채로 전달받는다.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Getter
@AllArgsConstructor
@ToString
public class JwtDTO {
	private final String accessToken;
	private final String refreshToken;
}
