package com.github.koobh95.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.koobh95.data.model.dto.JwtDTO;
import com.github.koobh95.data.model.dto.UserInfoDTO;
import com.github.koobh95.data.model.dto.request.LoginRequest;
import com.github.koobh95.data.model.dto.request.PasswordResetRequest;
import com.github.koobh95.data.model.dto.request.SignUpRequest;
import com.github.koobh95.data.model.entity.User;
import com.github.koobh95.data.model.entity.UserToken;
import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.data.repository.UserRepository;
import com.github.koobh95.data.repository.UserTokenRepository;
import com.github.koobh95.exception.LoginException;
import com.github.koobh95.exception.SignUpException;
import com.github.koobh95.exception.UserModifyException;
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
		
		Optional<User> user = userRepository.findById(id);
		
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
	 * - 클라이언트의 로그아웃 요청을 처리한다. DB에 저장된 토큰 정보를 삭제한다.
	 * - 클라이언트는 로그아웃 버튼을 누르는 시점에서 네트워크 연결 여부, 서버 상태와 상관없이 클라이언트가
	 *  가진 인증 정보 및 사용자 정보를 모두 삭제하기 때문에 이 작업이 반드시 성공해야하는 것은 아니다.
	 * 
	 * @param userId 로그아웃을 요청한 사용자의 ID
	 */
	@Override
	public void logout(String userId) {
		Optional<UserToken> userToken = userTokenRepository.findById(userId);
		if(userToken.isPresent())
			userTokenRepository.deleteById(userId);
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

	/**
	 * - 회원가입 요청을 처리하는 메서드다.
	 * - SignUpRequest에는 회원가입 시 필요한 필수 입력값들이 암호화된 채로 초기화되어 있다. 각 
	 * 데이터에 대한 제한 사항은 클라이언트 측에서 검증했으므로 여기서는 DB에 저장된 유저 데이터들과 중복
	 * 검사만 수행한다. 
	 * - 새로운 사용자 정보를 DB에 저장할 때 비밀번호는 단방향 암호화한 채로 저장한다.
	 * 
	 * @param signUpRequest 
	 */
	@Override
	public void signUp(SignUpRequest signUpRequest) {
		final String id = aes256Util.decrypt(signUpRequest.getUserId());
		final String password = aes256Util.decrypt(signUpRequest.getPassword());
		final String nickname = aes256Util.decrypt(signUpRequest.getNickname());
		final String email = aes256Util.decrypt(signUpRequest.getEmail());
		
		// 아이디 중복 검사
		userRepository.findById(id).ifPresent(m -> {
			throw new SignUpException(ErrorCode.USER_ID_DUPLICATED, "id=" + id);
		});
		
		// 닉네임 중복 검사
		userRepository.findByNickname(nickname).ifPresent(m -> {
			throw new SignUpException(ErrorCode.NICKNAME_DUPLICATED, 
					"nickname=" + nickname);
		});
		
		// 이메일 중복 검사
		userRepository.findByEmail(email).ifPresent(m -> {
			throw new SignUpException(ErrorCode.EMAIL_DUPLICATED, 
					"email=" + email);
		});
		
		String encryptedPassword = passwordEncoder.encode(password); // 암호화
		userRepository.save(
				User.createNewUser(id, encryptedPassword, nickname, email));
	}

	/**
	 * - 비밀번호 변경 요청을 처리하는 메서드다.
	 * - PasswordResetRequest 객체가 가진 아이디와 비밀번호는 양방향 암호화된 상태이기 때문에 
	 *  복호화한 뒤 데이터를 검증에 사용한다.  
	 * - 비밀번호 변경 API가 호출되려면 기본적으로 계정 인증이 수행되어야 하기 때문에 계정 데이터 존재
	 *  여부는 별도로 확인하지 않는다.
	 * - 비밀번호를 변경하려면 현재 사용 중인 비밀번호와 일치하지 않으면서 이전에 사용한 비밀번호와도
	 *  일치하지 않아야 한다. 두 조건을 모두 만족했을 경우 비밀번호를 변경한다.  
	 * 
	 * @param request 
	 */
	@Transactional
	@Override
	public void passwordReset(PasswordResetRequest request) {
		final String userId = aes256Util.decrypt(request.getUserId());
		final String newPassword = aes256Util.decrypt(request.getPassword());
		User user = userRepository.findById(userId).get();
		
		if(passwordEncoder.matches(newPassword, user.getPassword()))
			throw new UserModifyException(ErrorCode.PASSWORD_CURRENTLY_USE,
					"id=" + userId);

		if(user.getLastPassword() != null && 
				passwordEncoder.matches(newPassword, user.getLastPassword()))
			throw new UserModifyException(ErrorCode.PASSWORD_PREVIOUSLY_USE,
					"id=" + userId);

		user.updatePassword(passwordEncoder.encode(newPassword));
	}

	/**
	 * - 닉네임 변경 요청을 처리한다.
	 * - 닉네임 변경을 위해서는 클라이언트에서 로그인이 필요하기 때문에 계정의 존재 여부는 검증하지 않는다.
	 * - 변경하려는 닉네임과 같은 데이터가 DB에 존재하는지 확인하여 없을 경우 닉네임을 갱신한다.
	 * 
	 * @param userId 
	 * @param newNickname 
	 */
	@Transactional
	@Override
	public void nicknameChange(String userId, String newNickname) {
		userRepository.findByNickname(newNickname).ifPresent(m -> {
			throw new UserModifyException(ErrorCode.NICKNAME_DUPLICATED, 
					"id=" + userId + ", nickname=" + newNickname);
		});
		
		User user = userRepository.findById(userId).get();
		user.updateNickname(newNickname);
	}

	/**
	 * 로그인된 사용자가 비밀번호를 변경하기 위해 현재 비밀번호를 검증한다.
	 * 
	 * @param userId 사용자 아이디
	 * @param encryptedPassword 암호화된 비밀번호 
	 */
	@Override
	public void currnetPasswordAuth(String userId, String encryptedPassword) {
		final String password = aes256Util.decrypt(encryptedPassword);
		User user = userRepository.findById(userId).get();
		if(!passwordEncoder.matches(password, user.getPassword()))
			throw new UserModifyException(ErrorCode.PASSWORD_MISMATCH, 
					"id=" + userId);
	}
}
