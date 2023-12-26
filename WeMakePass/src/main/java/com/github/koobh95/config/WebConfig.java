package com.github.koobh95.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * xml 기반의 스프링 프로젝트에서 web.xml을 대체하는 설정 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
public class WebConfig 
		extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * Root Web Application Context 관련 설정 파일 등록
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { 
					AppConfig.class,
					SecurityConfig.class };
	}

	/**
	 * Servlet Application Context 관련 설정 파일 등록
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ServletConfig.class };
	}

	/**
	 * Dispatcher Servlet에 매핑할 주소 세팅("/" = 모든 요청)
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	/**
	 * Filter 등록
	 * - 문자셋 인코딩 필터
	 * - SecurityFilterChain에 접근하기 위한 DelegatingFilterProxy 등록
	 */
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { getCharacterEncodingFilter(),
				new DelegatingFilterProxy("springSecurityFilterChain")};
	}
	
	/**
	 * 문자셋을 UTF-8으로 인코딩하는 필터 반환
	 * @return
	 */
	private CharacterEncodingFilter getCharacterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = 
				new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}

}
