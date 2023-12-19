package com.github.koobh95.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.RequiredArgsConstructor;

/**
 * 데이터 베이스 관련 설정 클래스 
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
@Configuration
@EnableTransactionManagement // Transaction 허용
@EnableJpaRepositories("com.github.koobh95.data.repository") // Repository 클래스 스캔
@PropertySource("classpath:config/oracle.properties") // DB 정보를 가진 파일 참조
@RequiredArgsConstructor
public class DBConfig {
	private final Environment environment;

	// EntityMangerFactory bean으로 생성
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em 
			= new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { 
				"com.github.koobh95.data.model.entity" });
		
		HibernateJpaVendorAdapter venderAdapter = new HibernateJpaVendorAdapter(); 
		em.setJpaVendorAdapter(venderAdapter);
		em.setJpaProperties(getProperties());
		return em;
	}
	
	// DB와 관계된 커넥션 정보를 가지는 DataSource 객체를 bean으로 생성
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl(environment.getProperty("db.url"));
		dataSource.setUsername(environment.getProperty("db.username"));
		dataSource.setPassword(environment.getProperty("db.password"));
		return dataSource;
	}
	
	// JPA를 지원하는 TransactionManager를 Bean으로 생성
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = 
				new JpaTransactionManager(entityManagerFactory().getObject());
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
	
	/**
	 *  Repository 어노테이션이 선언된 클래스에서 발생하는 JPA 예외를 스프링 프레임워크가 제공하는
	 * 추상화된 예외로 변경.
	 */
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){ 
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	// EntityMangerFactory 생성에 사용되는 프로퍼티를 생성하여 반환.
	private Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", 
				"org.hibernate.dialect.Oracle10gDialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		return properties;
	}
}
