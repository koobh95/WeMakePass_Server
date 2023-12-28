package com.github.koobh95.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.github.koobh95.annotation.LoginRequired;
import com.github.koobh95.data.model.WmpUserDetails;
import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.exception.AccountValidationException;

/**
 * - 발생한 요청이 시큐리티가 적용된 자원에 접근하려는 경우 계정의 상태(탈퇴 여부)를 검증한다.
 * - 보안이 적용된 자원인지 판단하는 기준은 Controller 클래스가 가진 메소드에 LoginRequired
 *  어노테이션이 적용되었는지에 따라 결정된다.
 * - 검증할 데이터는 SecurityContext에 저장된 Authentication 객체를 사용한다. 
 * 
 * @author BH-Ku
 * @since 2023-12-28
 */
public class AccountValidationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		if(handlerMethod.getMethodAnnotation(LoginRequired.class) != null) {
			Authentication authentication = 
					SecurityContextHolder.getContext().getAuthentication();
			WmpUserDetails userDetails = (WmpUserDetails)authentication.getDetails();
			if(userDetails.isWithdraw())
				throw new AccountValidationException(ErrorCode.WITHDRAW_ACCOUNT,
						"id=" + userDetails.getUsername());
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
