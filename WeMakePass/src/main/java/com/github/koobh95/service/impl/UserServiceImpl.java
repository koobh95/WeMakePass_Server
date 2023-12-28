package com.github.koobh95.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.koobh95.data.model.dto.JwtDTO;
import com.github.koobh95.data.model.dto.UserInfoDTO;
import com.github.koobh95.data.model.dto.request.LoginRequest;
import com.github.koobh95.data.model.entity.User;
import com.github.koobh95.data.model.entity.UserToken;
import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.data.repository.UserRepository;
import com.github.koobh95.data.repository.UserTokenRepository;
import com.github.koobh95.exception.LoginException;
import com.github.koobh95.security.util.AES256Util;
import com.github.koobh95.security.util.JwtProvider;
import com.github.koobh95.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * 아이디/비밀번호 기반의 사용자 인증, 사용자 정보 변경 등의 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
	private final UserTokenRepository userTokenRepository;

	private final JwtProvider jwtProvider;
	private final AES256Util aes256Util;
	private final PasswordEncoder passwordEncoder;
	
	/**
	 * - 사용자가 로그인을 시도할 때 호출되는 메서드.
	 * - 아이디의 존재 여부, 비밀번호 일치 여부, 계정 탈퇴 여부, 계정 인증 여부 등을 검증한다.
	 * - 검증이 완료된 경우 AccessToken과 RefreshToken을 발급한다. 해당 사용자 토큰 정보가 DB에
	 *  존재하는지 확인한 뒤 존재할 경우 토큰을 갱신하고 존재하지 않는다면 발급한 토큰을 새로 삽입한다.
	 * - 토큰 정보가 DB에 존재한다는 것은 이전에 로그인한 후 정상적으로 로그아웃되지 않았음을 의미한다.
	 * 
	 * @param userRequest 클라이언트가 보낸 유저 아이디와 비밀번호를 가지고 있으며 비밀번호는
	 *        AES256 방식으로 암호화되어 있다.
	 */
	@Transactional
	@Override
	public JwtDTO login(LoginRequest userRequest) {
		final String id = aes256Util.decrypt(userRequest.getUserId());
		final String password = aes256Util.decrypt(userRequest.getPassword());
		
		Optional<User> user = userRepository.findByUserId(id);
		
		// 아이디 존재 여부 검증
		user.orElseThrow(() -> 
			new LoginException(ErrorCode.USER_ID_NOT_FOUND, "id=" + id));
		
		// 비밀번호 일치 여부 검증
		if(!passwordEncoder.matches(password, user.get().getPassword()))
			throw new LoginException(ErrorCode.PASSWORD_MISMATCH, "id=" + id);
		
		// 계정 탈퇴 여부 검증
		if(user.get().getWithdrawDate() != null)
			throw new LoginException(ErrorCode.WITHDRAW_ACCOUNT, "id=" + id);
		
		// 계정 인증 여부 검증
		if(user.get().getCert() =='N')
			throw new LoginException(ErrorCode.UNCERT_USER, "id=" + id);
		
		String refreshToken = jwtProvider.createRefreshToken();
		Optional<UserToken> userToken = userTokenRepository.findById(id);
		
		// 토큰의 존재 여부를 확인하여 존재할 경우 update, 아닐 경우 insert 
		userToken.ifPresentOrElse(ut -> 
			userToken.get().updateRefreshToken(refreshToken), () -> 
				userTokenRepository.save(new UserToken(id, refreshToken)));
		
		return new JwtDTO(
				jwtProvider.createAccessToken(user.get().getUserId()), 
				aes256Util.encrypt(refreshToken));
	}

	/**
	 * - 로그인에 성공했을 때 클라이언트 내부에 저장할 사용자 정보를 반환한다.
	 * - 로그인에 성공한 다음 즉시 호출된다는 특성상 유저에 대한 별도의 검사(인증, 탈퇴 등)는 수행하지
	 *  않는다.
	 * 
	 * @param userId 
	 */
	@Transactional(readOnly = true)
	@Override
	public UserInfoDTO userInfo(String userId) {
		Optional<User> user = userRepository.findById(userId);
		UserInfoDTO userInfo = new UserInfoDTO(
				aes256Util.encrypt(user.get().getUserId()),
				aes256Util.encrypt(user.get().getNickname()),
				aes256Util.encrypt(user.get().getEmail()));
		return userInfo;
	}
}
