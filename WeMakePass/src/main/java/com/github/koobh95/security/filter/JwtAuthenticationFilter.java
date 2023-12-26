package com.github.koobh95.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.koobh95.data.model.WmpUserDetails;
import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.security.util.JwtProvider;
import com.github.koobh95.service.impl.WmpUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * - 클라이언트가 보낸 Request의 Header에서 JWT을 파싱하고 검증하는 필터 클래스.
 * - SecurityConfig 클래스에서 bean으로 등록된다.
 * - UsernamePasswordAuthenticationFilter 전에 실행된다.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final WmpUserDetailsService wmpUserDetailsService;
	
	public static final String JWT_EXCEPTION_ATTRIBUTE_NAME = "jwtException";
	
	/**
	 * - Token을 파싱하여 SecurityContext에 Authentication 객체를 초기화한다.
	 * - Token 파싱에 실패할 경우 실패한 사유(에러 코드)를 Request 객체에 세팅한 뒤 다음 필터로
	 *  체인한다.
	 * - Token 파싱에 실패하였을 때 사유가 토큰 만료인 경우 "EXPIRED_ACCESS_TOKEN"을, 다른 
	 *  사유일 경우 "INVALID_ACCESS_TOKEN"을 에러 코드로 설정한다.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
		String token = parseToken(request);
		
		try {
			if(StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
				WmpUserDetails userDetails = wmpUserDetailsService
						.loadUserByUsername(jwtProvider.parseUserId(token));
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(
								userDetails.getUsername(),
								null, null);
				authentication.setDetails(userDetails);
				SecurityContextHolder.getContext()
						.setAuthentication(authentication);
			}
		} catch(ExpiredJwtException e) {
			request.setAttribute(JWT_EXCEPTION_ATTRIBUTE_NAME, 
					ErrorCode.EXPIRED_ACCESS_TOKEN);
			log.error(jwtProvider.getErrorMessage(e.getClass()));
		} catch(SecurityException | MalformedJwtException 
				| UnsupportedJwtException | IllegalArgumentException e) {
			request.setAttribute(JWT_EXCEPTION_ATTRIBUTE_NAME,
					ErrorCode.INVALID_ACCESS_TOKEN);
			log.error(jwtProvider.getErrorMessage(e.getClass()));
		} catch (Exception e) {
			request.setAttribute(JWT_EXCEPTION_ATTRIBUTE_NAME,
					ErrorCode.INVALID_ACCESS_TOKEN);
			log.error(jwtProvider.getErrorMessage(e.getClass()));
		}
		
		filterChain.doFilter(request, response);
	}

	/**
	 * - Request의 Header에서 토큰을 파싱하여 반환한다.
	 * - 토큰이 있다면 "Authorization" 속성에 존재하기 때문에 값을 읽어온 뒤 토큰 타입
	 *  ("Bearer ")의 길이 만큼 잘라내어 토큰 값을 추출한다. 만약 토큰이 없거나 토큰 타입을 파싱해내지
	 *  못했을 경우 null을 반환한다.
	 *  
	 * @param request
	 * @return
	 */
	private String parseToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(JwtProvider.HEADER_AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && 
				bearerToken.startsWith(JwtProvider.TOKEN_TYPE))
			return bearerToken.substring(7, bearerToken.length());
		return null;
	}
}
