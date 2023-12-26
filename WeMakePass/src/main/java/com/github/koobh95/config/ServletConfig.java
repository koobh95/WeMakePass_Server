package com.github.koobh95.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * xml 기반의 스프링 프로젝트에서 servlet-context.xml을 대체하는 설정 클래스
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { 
		"com.github.koobh95.controller",
		"com.github.koobh95.exception.handler"})
public class ServletConfig implements WebMvcConfigurer {
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/", ".jsp");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/resources/**")
			.addResourceLocations("/resources/");
	}
}
