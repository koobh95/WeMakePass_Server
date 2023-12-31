package com.github.koobh95.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.koobh95.data.model.entity.UserCert;

/**
 * UserCert 클래스와 대응되는 Repository 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-30
 */
public interface UserCertRepository extends JpaRepository<UserCert, String> {
	
}
