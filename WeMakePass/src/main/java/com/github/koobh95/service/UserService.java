package com.github.koobh95.service;

import com.github.koobh95.data.model.dto.JwtDTO;
import com.github.koobh95.data.model.dto.UserInfoDTO;
import com.github.koobh95.data.model.dto.request.LoginRequest;
import com.github.koobh95.data.model.dto.request.PasswordResetRequest;
import com.github.koobh95.data.model.dto.request.SignUpRequest;

/**
 * 아이디/비밀번호 기반의 사용자 인증, 사용자 정보 변경 등의 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
public interface UserService {
	// 로그인 요청 처리
	JwtDTO login(LoginRequest userRequest);
	// 사용자가 클라이언트에 저장할 데이터를 반환
	UserInfoDTO userInfo(String userId);
	// 회원가입 요청 처리
	void signUp(SignUpRequest signUpRequest);
	// 비밀번호 변경
	void passwordReset(PasswordResetRequest request);
}
