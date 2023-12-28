package com.github.koobh95.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.koobh95.data.model.dto.JwtDTO;
import com.github.koobh95.data.model.dto.request.JwtReissueRequest;
import com.github.koobh95.data.model.entity.UserToken;
import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.data.repository.UserTokenRepository;
import com.github.koobh95.exception.JwtReissueException;
import com.github.koobh95.security.util.AES256Util;
import com.github.koobh95.security.util.JwtProvider;
import com.github.koobh95.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

/**
 * JWT 관련 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2023-12-28
 */
@Service("jwtService")
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
	private final UserTokenRepository userTokenRepository;
	private final AES256Util aes256Util;
	private final JwtProvider jwtProvider;
	
	/**
	 * - 클라이언트로부터 사용자의 ID와 보유한 RefreshToken을 받아 토큰 재발급을 진행한다.
	 * - 토큰 재발급을 하기 위해 다음 사항들을 검증한다.
	 *  > 요청한 사용자의 아이디와 일치하는 인증 정보가 DB에 존재하는가
	 *  > 가장 최근에 발급한 토큰과 일치하는가
	 *  > 토큰이 만료되었는가
	 *  > 토큰이 유효한가
	 * - 토큰이 검증되면 새로운 토큰을 생성한 뒤 DB에 저장하고 토큰을 암호화하여 클라이언트에게 반환한다.
	 * - 토큰은 암호화되어 전송된 뒤 재발급 시에 복호화된 채로 전송받는다. 따라서 재발급 성공 여부와
	 *  상관없이 토큰이 이미 노출되었다고 가정하고 이 메서드가 종료되는 순간 관련 데이터를 DB에서 삭제한다. 
	 * 
	 * @param tokenReissueRequest 재발급 대상 사용자의 아이디와 RefreshToken을 가진 객체 
	 */
	@Transactional
	@Override
	public JwtDTO reissueToken(JwtReissueRequest tokenReissueRequest) {
		final String id = tokenReissueRequest.getUserId();
		final String refreshToken = tokenReissueRequest.getRefreshToken();
		
		Optional<UserToken> userToken = userTokenRepository.findById(id);
		userToken.orElseThrow(() -> new JwtReissueException(
				ErrorCode.INVALID_REFRESH_TOKEN, 
				"토큰 정보를 찾을 수 없습니다.(id=" + id + ')'));
		
		// Client로부터 받은 RefreshToken과 DB에 저장된 RefreshToken의 일치 여부
		if(!userToken.get().getRefreshToken().equals(refreshToken)) {
			throw new JwtReissueException(ErrorCode.INVALID_REFRESH_TOKEN,
					"이전에 발급한 토큰과 일치하지 않습니다.(id=" + id + ')');
		}

		// RefreshToken이 유효한지 확인
		try {
			jwtProvider.validateToken(refreshToken);
		} catch(ExpiredJwtException e) {
			userTokenRepository.deleteById(id);
			throw new JwtReissueException(ErrorCode.INVALID_REFRESH_TOKEN,
					"토큰이 만료되었습니다. (id=" + id + ')');
		} catch(Exception e) {
			userTokenRepository.deleteById(id);
			throw new JwtReissueException(ErrorCode.INVALID_REFRESH_TOKEN,
					"토큰 검증에 실패했습니다. (" +
							jwtProvider.getErrorMessage(e.getClass()) + 
							", (id=" + id + ')');
		}
		
		String newRefreshToken = jwtProvider.createRefreshToken();
		userToken.get().updateRefreshToken(newRefreshToken);
		return new JwtDTO(jwtProvider.createAccessToken(id),
				aes256Util.encrypt(newRefreshToken));
	}
}
