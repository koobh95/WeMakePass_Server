package com.github.koobh95.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 요청을 처리할 때 멀티 스레드로 작업을 처리하기 위한 ThreadPool을 구현하는 설정 클래스. 
 * 
 * @author BH-Ku
 * @since 2023-12-29
 */
@Configuration
@EnableAsync
public class AsyncConfig {
	@Bean
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(50);
		return executor;
	}
}
