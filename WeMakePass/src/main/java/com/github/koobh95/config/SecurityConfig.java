package com.github.koobh95.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * 스프링 시큐리티 설정 클래스
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private String[] permitUris = { };

	/**
	 * 스프링 시큐리티 필터 체인 설정
	 * - 정책, 기본 필터 각각에 대한 사용 유무, 보안을 적용할 리소스, 보안을 적용하지 않을 리소스 등을
	 *  설정한다.
	 * 
	 * @param http 기본 필터에 대한 설정, 특정 자원에 대한 접근 제한 유무를 설정 
	 * @return 
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception{
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.httpBasic().disable() // httpBasic 인증 비활성화
			.csrf().disable() // CsrfFilter disable. API 환경에서 필요없음.
			.cors(); // CorsFilter enable
		
		http.authorizeHttpRequests()
			.requestMatchers(permitUris).permitAll() // 보안을 적용하지 않을 URI 설정 
			.anyRequest().authenticated(); // 모든 리소스에 보안 설정
		
		return http.build();
	}
	
	/**
	 *  MvcRequestMatcher를 사용하기 위해서는 HandlerMappingIntrospector 객체가 bean으로
	 * 등록되어 있어야 한다.
	 * 
	 * @return
	 */
	@Bean(name="mvcHandlerMappingIntrospector")
	public HandlerMappingIntrospector handlerMappingIntrospector() {
		return new HandlerMappingIntrospector();
	}
}
