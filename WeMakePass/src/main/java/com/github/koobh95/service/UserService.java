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
	// 로그아웃 요청 처리
	void logout(String userId);
	// 사용자가 클라이언트에 저장할 데이터를 반환
	UserInfoDTO userInfo(String userId);
	// 회원가입 요청 처리
	void signUp(SignUpRequest signUpRequest);
	// 비밀번호 변경
	void passwordReset(PasswordResetRequest request);
	// 닉네임 변경
	void nicknameChange(String userId, String newNickname);
	// 비밀번호 변경을 위해 현재 비밀번호를 검증한다.
	void currnetPasswordAuth(String userId, String encryptedPassword);
}
