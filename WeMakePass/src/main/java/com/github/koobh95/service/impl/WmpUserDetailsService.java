package com.github.koobh95.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.koobh95.data.model.WmpUserDetails;
import com.github.koobh95.data.model.entity.User;
import com.github.koobh95.data.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Authentication에 초기화되는 UserDetails 객체를 조회하는데 사용되는 서비스 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Service
@RequiredArgsConstructor
public class WmpUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	
	/**
	 *  AccessToken에서 파싱된 UserId를 파라미터로 받아 DB에서 데이터를 조회한다. 정상적으로
	 * 조회되었을 경우 User 객체를 WmpUserDetails 객체로 변환하여 반환한다.
	 */
	@Override
	public WmpUserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUserId(username);
		
		if(!user.isPresent())
			throw new UsernameNotFoundException(username);
		
		return WmpUserDetails.of(user.get());
	}
}
