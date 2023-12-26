package com.github.koobh95.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * xml 기반의 스프링 프로젝트에서 root-context.xml을 대체하는 설정 클래스
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
@Configuration
@Import({ DBConfig.class })
@ComponentScan(basePackages = { "com.github.koobh95.service.impl" })
public class AppConfig {
	
}
