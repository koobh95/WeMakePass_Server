package com.github.koobh95.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.koobh95.data.model.entity.User;

/**
 * User 클래스와 대응되는 Repository 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
public interface UserRepository extends JpaRepository<User, String>{
	/**
	 * 특정 유저의 Id와 일치하는 유저 정보를 조회한다. 
	 * 
	 * @param userId DB에서 조회하고자 하는 아이디
	 * @return
	 */
	Optional<User> findByUserId(String userId);
}
