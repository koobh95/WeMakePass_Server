package com.github.koobh95.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.github.koobh95.exception.handler.JwtAuthenticationEntryPoint;
import com.github.koobh95.security.filter.JwtAuthenticationFilter;
import com.github.koobh95.security.util.AES256Util;
import com.github.koobh95.security.util.JwtProvider;
import com.github.koobh95.service.impl.WmpUserDetailsService;

import lombok.RequiredArgsConstructor;

/**
 * 스프링 시큐리티 설정 클래스
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
@Configuration
@EnableWebSecurity
@Import({EncoderConfig.class})
@RequiredArgsConstructor
@PropertySource({
	"classpath:config/jwt.properties",
	"classpath:config/aes.properties"})
public class SecurityConfig {
	private final Environment environment;
	
	private final WmpUserDetailsService wmpUserDetailsService;
	
	// 보안을 적용하지 않을 URI
	private String[] permitUris = { "/api/user/login"};
	
	/**
	 * - 스프링 시큐리티 필터 체인 설정
	 * - 정책, 기본 필터 각각에 대한 사용 유무, 보안을 적용할 리소스, 보안을 적용하지 않을 리소스 등을
	 *  설정한다.
	 * 
	 * @param http 기본 필터에 대한 설정, 특정 자원에 대한 접근 제한 유무를 설정 
	 * @return 
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
			throws Exception {
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.httpBasic().disable() // httpBasic 인증 비활성화
			.csrf().disable() // CsrfFilter disable. API 환경에서 필요없음.
			.cors(); // CorsFilter enable
		
		http.addFilterBefore(jwtAuthenticationFilter(), 
				UsernamePasswordAuthenticationFilter.class);
		
		http.authorizeHttpRequests()
			.requestMatchers(permitUris).permitAll() // 보안을 적용하지 않을 URI 설정 
			.anyRequest().authenticated(); // 모든 리소스에 보안 설정
		
		http.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint());
		
		return http.build();
	}
	
	// MvcRequestMatcher를 사용하기 위해서는 이 객체가 bean으로 등록되어 있어야 한다.
	@Bean(name="mvcHandlerMappingIntrospector")
	public HandlerMappingIntrospector handlerMappingIntrospector() {
		return new HandlerMappingIntrospector();
	}
	
	// JWT 인증을 담당하는 필터를 Bean으로 등록.
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtProvider(), wmpUserDetailsService);
	}
	
	// JWT를 생성, 검증하는 유틸리티 클래스
	@Bean
	public JwtProvider jwtProvider() {
		JwtProvider jwtProvider = new JwtProvider();
		jwtProvider.initSecretKey(environment.getProperty("jwt.secret_key"));
		return jwtProvider;
	}
	
	// JWT 인증 실패 시 발생하는 AuthenticationException을 핸들링하는 클래스 
	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint();
	}
	
	// AES256 암호화 유틸리티 클래스
	@Bean
	public AES256Util aes256Util() {
		return new AES256Util(environment.getProperty("aes.secret_key"),
				environment.getProperty("aes.iv"));
	}
}
