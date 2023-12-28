package com.github.koobh95.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.koobh95.data.model.dto.JwtDTO;
import com.github.koobh95.data.model.dto.request.JwtReissueRequest;
import com.github.koobh95.service.JwtService;

import lombok.RequiredArgsConstructor;

/**
 * Jwt를 재발급하는 API를 제공.
 * 
 * @author BH-Ku
 * @since 2023-12-28
 */
@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
public class JwtController {
	private final JwtService jwtService;

	/**
	 * - 인증하려는 사용자의 아이디와 RefreshToken을 받아 토큰의 유효성을 확인한 뒤 유효하다면 새로운
	 * RefreshToken과 AccessToken을 발급한다.
	 * - 이 API는 사용자가 보유한 AccessToken의 유효 기간이 만료되었을 때 호출된다.
	 * 
	 * @param tokenReissueRequest 재발급 대상 사용자의 아이디와 RefreshToken을 가진 객체
	 * @return
	 */
	@PostMapping(value = "/reissue")
	public JwtDTO reissue(
			@RequestBody JwtReissueRequest jwtReissueRequest) {
		return jwtService.reissueToken(jwtReissueRequest);
	}
}
