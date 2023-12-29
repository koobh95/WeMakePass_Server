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
	// 특정 닉네임을 가진 유저를 조회한다.
	Optional<User> findByNickname(String nickname);

	// 특정 이메일을 가진 유저를 조회한다.
	Optional<User> findByEmail(String email);
}
