package com.github.koobh95.exception.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.koobh95.data.model.dto.response.ErrorResponse;
import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.security.filter.JwtAuthenticationFilter;

import lombok.extern.log4j.Log4j2;

/**
 * - JWT 인증 실패를 처리하기 위해 생성된 클래스.
 * - SecurityConfig 클래스에서 bean으로 등록된다.
 * - 발생한 에러를 확인하여 JWT 인증 실패가 원인일 경우 반환할 Response를 수정한다. 
 * - JWT를 파싱하는 커스텀 필터인 JwtAuthenticationFilter에서 Token을 파싱하는데 실패했을 경우
 *  FilterSecurityInterceptor에서 SecurityContext를 확인했을 때 인증 객체
 *  (Authentication)가 없기 때문에 AuthenticationException이 발생하고 이 클래스에서 예외를
 *  캐치하게 된다.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Log4j2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper objMapper = new ObjectMapper();

	/**
	 *  이 메서드가 호출되었다는 것은 클라이언트로부터 받은 요청이 인증된 자원에 접근하려고 했으나 시큐리티
	 * 필터들이 실행되는 과정에서 인증에 실패했다는 것을 의미한다. 발생한 예외가 JWT 파싱 과정에서 
	 * 발생한 예외인지 확인하여 그에 따른 ErrorResponse를 생성하고 Response 객체를 수정한다.
	 */
	@Override
	public void commence(HttpServletRequest request, 
			HttpServletResponse response, AuthenticationException authException)
					throws IOException, ServletException {
		try {
			String errorCode = (String)request.getAttribute(
					JwtAuthenticationFilter.JWT_EXCEPTION_ATTRIBUTE_NAME);
			
			if(StringUtils.hasText(errorCode)) { // JWT 에러
				response.setContentType("application/json; charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write(objMapper.writeValueAsString(
						createErrorResponse(errorCode)));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch(ClassCastException e) {
			log.error("JWT 인증 예외 여부를 확인하는 도중 ClassCastException이 발생했습니다.");
			log.error(e.getMessage());
		}
	}
	
	/**
	 * - 클라이언트에 반환할 AccessToken 인증 실패 관련 ErrorResponse를 객체화하여 반환한다.
	 * - 여기서 ErrorResponse를 초기화할 때 사용하는 ErrorCode는 EXPIRED_ACCESS_TOKEN
	 *  (기한 만료), INVALID_ACCESS_TOKEN(잘못된 토큰) 두 가지다. 
	 * - 에러 코드가 EXPIRED_ACCESS_TOKEN인 경우 클라이언트 측에게 RefreshToken을 보내게 하고
	 *  INVALID_ACCESS_TOKEN인 경우 클라이언트가 가진 토큰 정보를 초기화시켜 인증(로그인)을 
	 *  재시도하게 한다. 인증에 실패한 사유가 기간 만료가 아니라면 결국 정상적인 토큰이 아닐 가능성이 높다.
	 *  또한 사유를 클라이언트에 알려준들 처리할 방법도 마땅치 않기 때문에 기간 만료 이 외에는 
	 *  INVALID_ACCESS_TOKEN를 반환한다.
	 * 
	 * @param errorCode HttpServletRequest 객체에서 파싱한 에러 코드
	 * @return
	 */
	private ErrorResponse createErrorResponse(String errorCode) {
		String errMsg = errorCode.equals(ErrorCode.EXPIRED_ACCESS_TOKEN.name())
				? ErrorCode.EXPIRED_ACCESS_TOKEN.getMessage()
						: ErrorCode.INVALID_ACCESS_TOKEN.getMessage();
		return new ErrorResponse(errorCode, errMsg);
	}
}
