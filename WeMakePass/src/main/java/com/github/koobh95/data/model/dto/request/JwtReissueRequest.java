package com.github.koobh95.data.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 클라이언트가 토큰 재발급을 요청할 때 사용되는 DTO 클래스.
 * 
 * @author BH-Ku
 * @since 20323-12-28
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtReissueRequest {
	private String userId; // 토큰 재발급을 요청한 유저의 아이디
	private String refreshToken; // 복호화된 RefreshToken
}
