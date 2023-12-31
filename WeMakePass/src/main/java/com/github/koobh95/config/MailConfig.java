package com.github.koobh95.config;

import java.util.Properties;

import javax.net.ssl.SSLSocketFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.RequiredArgsConstructor;

/**
 * JavaMailSender 인터페이스를 구현하는 이메일 설정 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-30
 */
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:config/mail.properties")
public class MailConfig {
	private final Environment environment;
	
	@Bean
	public JavaMailSenderImpl javaMailSenderImpl() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		String tlsPortNumber = environment.getProperty("mail.tls_port_number");
		String hostName = environment.getProperty("mail.host_name");
		String username = environment.getProperty("mail.username");
		String password = environment.getProperty("mail.password");
		
		javaMailSender.setHost(hostName);
		javaMailSender.setPort(Integer.valueOf(tlsPortNumber));
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		javaMailSender.setJavaMailProperties(getMailProperties());
		javaMailSender.setDefaultEncoding("UTF-8");
		return javaMailSender;
	}
	
	private Properties getMailProperties() {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp"); // 프로토콜 설정
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.ssl.enabled", false); // SSL 사용안함
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.socketFactory.class", SSLSocketFactory.class);
		return properties;
	}
}
