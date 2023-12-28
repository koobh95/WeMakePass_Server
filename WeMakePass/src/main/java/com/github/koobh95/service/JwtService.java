package com.github.koobh95.service;

import com.github.koobh95.data.model.dto.JwtDTO;
import com.github.koobh95.data.model.dto.request.JwtReissueRequest;

/**
 * JWT 관련 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2023-12-28
 */
public interface JwtService {
	// 클라이언트로부터 받은 RefreshToken의 유효성을 검증하여 토큰을 재발급한다.
	public JwtDTO reissueToken(JwtReissueRequest tokenReissueRequest);
}
