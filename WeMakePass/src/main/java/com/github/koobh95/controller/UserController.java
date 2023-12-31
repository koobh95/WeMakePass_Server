package com.github.koobh95.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.koobh95.data.model.dto.JwtDTO;
import com.github.koobh95.data.model.dto.UserInfoDTO;
import com.github.koobh95.data.model.dto.request.LoginRequest;
import com.github.koobh95.data.model.dto.request.PasswordResetRequest;
import com.github.koobh95.data.model.dto.request.SignUpRequest;
import com.github.koobh95.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * 로그인, 회원가입, 사용자 계정 정보 수정과 관련된 API를 제공.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	/**
	 * 로그인 요청
	 * 
	 * @param loginRequest 암호화된 아이디, 비밀번호를 가진 객체
	 * @return
	 */
	@PostMapping(value="/login")
	public JwtDTO login(@RequestBody LoginRequest loginRequest) {
		return userService.login(loginRequest);
	}
	
	/**
	 * 로그인 후 클라이언트에 저장할 사용자 정보 조회
	 * 
	 * @param authentication 인증 단계에서 저장한 인증 객체.
	 * @return
	 */
	@GetMapping(value="/info")
	public UserInfoDTO userInfo(Authentication authentication){
		return userService.userInfo(authentication.getName());
	}

	/**
	 * 회원가입 요청
	 * 
	 * @param signUpRequest 회원가입에 필요한 필수 정보를 갖는 DTO 클래스.
	 * @return
	 */
	@PutMapping(value="/sign_up")
	public String signUp(@RequestBody SignUpRequest signUpRequest) {
		userService.signUp(signUpRequest);
		return "회원가입이 완료되었습니다.";
	}
	
	/**
	 * 비밀번호 변경 요청
	 * 
	 * @param request 새로운 비밀번호와 변경 대상 아이디
	 * @return
	 */
	@PutMapping(value="/password_reset")
	public ResponseEntity<String> passwordReset(
			@RequestBody PasswordResetRequest request) {
		userService.passwordReset(request);
		return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
	}
}
