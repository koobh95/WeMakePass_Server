package com.github.koobh95.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *  PasswordEncoder를 bean으로 등록하는 역할만을 수행하는 설정 클래스다. 본래 SecurityConfig에서 
 * 등록하고 있었으나 테스트 과정에서 순환 참조가 발생하는 상황이 있었기에 별도의 클래스로 분리하였다. 
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Configuration
public class EncoderConfig {
	
	// 비밀번호를 단방향으로 암호화/복호화하는 하는 PasswordEncoder를 bean으로 등록한다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
