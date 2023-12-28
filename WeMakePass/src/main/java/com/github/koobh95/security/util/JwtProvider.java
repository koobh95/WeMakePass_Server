package com.github.koobh95.security.util;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

/**
 * - JWT를 생성, 파싱, 검증하는 유틸리티 클래스.
 * - SecurityConfig 클래스에서 bean으로 등록된다.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Log4j2
public class JwtProvider {
	private Key secretKey;

	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60 * 20L; // 20분
	private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 30L; // 1달
	public final static String HEADER_AUTHORIZATION = "Authorization";
	public final static String TOKEN_TYPE = "Bearer ";
	
	private final String EXPIRED_TOKEN_ERROR_MESSAGE = "토큰이 만료되었습니다.";
	private final String SIGNITURE_NOT_MATCH_TOKEN_ERROR_MESSAGE = 
			"토큰의 서명이 일치하지 않습니다.";
	private final String UNSUPPORTED_TOKEN_ERROR_MESSAGE = "지원하지 않는 토큰입니다.";
	private final String UNKNOWN_ERROR_MESSAGE = 
			"토큰 파싱 중 알 수 없는 에러가 발생했습니다.";

	/**
	 * - 토큰 생성, 파싱에 사용할 Key를 초기화한다. 
	 * - 이 클래스가 SecurityConfig에 의해 bean으로 초기화될 때 이 메서드가 호출된다.
	 * 
	 * @param key ".properties" 파일에서 읽은 32자리 key값
	 */
	public void initSecretKey(String key) {
		byte[] keyBytes = Decoders.BASE64.decode(key);
		secretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * - AccessToken을 생성하여 반환한다.
	 * - 아직까지 권한을 활용하고 있지 않기 때문에 권한 혹은 추가 데이터는 사용하지 않는다. 따라서 
	 *  Claims는 사용하지 않고 오로지 토큰 발급 대상의 아이디만 삽입한다. 
	 * 
	 * @param userId 토큰 발급 대상의 아이디
	 * @return AccessToken
	 */
	public String createAccessToken(String userId) {
		Date now = new Date(); // 현재 시간
		String accessToken = Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더의 타입을 JWT로 지정
				.signWith(secretKey, signatureAlgorithm) // 키, 알고리즘 지정
				.setSubject(userId) // 사용자 아이디 지정
				.setIssuedAt(now) // 발급 시간
				.setExpiration(new Date(now.getTime() + 
						ACCESS_TOKEN_EXPIRATION)) // 만료 시간 지정
				.compact();
		return accessToken;
	}

	/**
	 * - RefreshToken을 생성하여 반환한다.
	 * - 발급된 RefreshToken은 DB에 저장되기 때문에 User Id 같은 부가적인 데이터는 구태여 
	 *  Token에 삽입하지 않는다.
	 * 
	 * @return RefreshToken
	 */
	public String createRefreshToken() {
		Date now = new Date();
		String refreshToken = Jwts.builder()
				.signWith(secretKey, signatureAlgorithm) // 키, 알고리즘 지정
				.setExpiration(new Date(now.getTime() + 
						REFRESH_TOKEN_EXPIRATION)) // 만료 시간 지정
				.compact();
		return refreshToken;
	}

	/**
	 * AccessToken에서 userId를 파싱한다.
	 * 
	 * @param token
	 * @return UserId
	 */
	public String parseUserId(String accessToken) {
		return Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(accessToken)
				.getBody()
				.getSubject();
	}

	/**
	 * - AccessToken/RefreshToken의 유효성을 확인한다. 서버가 가진 고유 키를 통해 토큰을 파싱하고
	 *  성공할 경우 토큰이 유효하다고 판단한다. 
	 * - 토큰에 문제(기한 만료 등)가 있는 경우 메서드의 첫 번째 라인, 파싱을 수행하면서 Exception이
	 *  발생하여 메서드가 종료된다. 구태여 boolean을 반환해주지 않아도 되지만 호출하는 측에서 좀 더
	 *  직관적으로 보기 위해서 boolean을 반환하였다.
	 * - 토큰을 파싱하면서 발생할 수 있는 예외는 호출하는 측에서 처리하도록 하였다. 인증 필터에서 
	 *  AccessToken을 파싱하다가 예외가 발생하는 경우, Exception에 따라 각기 다른 ErrorCode를 
	 *  Request 객체에 세팅해야 하기 때문이다.
	 * 
	 * @param token 검증할 Token
	 * @return 
	 * @exception ExpiredJwtException, // 토큰 만료
	 * 		MalformedJwtException, // 토큰 서명 불일치
	 *		UnsupportedJwtException // 지원하지 않는 토큰
	 */
	public boolean validateToken(String token) throws Exception {
		Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
		return true;
	}
	
	/**
	 * - 토큰을 파싱하면서 발생한 Exception의 클래스를 파라미터로 받아 적절한 에러 메시지를 반환한다.
	 * - 토큰 검증 메서드에서 발생하는 Exception을 호출한 측으로 던지고 있는 만큼 호출하는 측에서
	 *  발생하는 예외에 따라 일관적인 에러 메시지를 출력하기 위해 사용하는 메서드다.  
	 * 
	 * @param clazz
	 * @return
	 */
	public String getErrorMessage(Class<? extends Exception> clazz) {
		if(clazz.equals(ExpiredJwtException.class)) 
			return EXPIRED_TOKEN_ERROR_MESSAGE;
		else if(clazz.equals(MalformedJwtException.class))
			return SIGNITURE_NOT_MATCH_TOKEN_ERROR_MESSAGE;
		else if(clazz.equals(UnsupportedJwtException.class))
			return UNSUPPORTED_TOKEN_ERROR_MESSAGE;
		return UNKNOWN_ERROR_MESSAGE;
	}
}
