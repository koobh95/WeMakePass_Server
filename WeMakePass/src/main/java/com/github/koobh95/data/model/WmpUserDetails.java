package com.github.koobh95.data.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.koobh95.data.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 사용자 인증 정보를 저장하는 모델 클래스로서 UserDetails를 상속받아 구현되는 커스텀 클래스다.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@AllArgsConstructor
@Builder
public class WmpUserDetails implements UserDetails{
	private String userId; // 사용자 아이디
	private char cert; // 계정 인증 여부
	private boolean withdraw; // 계정 탈퇴 여부
	
	public static WmpUserDetails of(User user) {
		return WmpUserDetails.builder()
				.userId(user.getUserId())
				.cert(user.getCert())
				.withdraw(user.getWithdrawDate() == null ? false : true)
				.build();
	}
	
	// 아이디
	@Override
	public String getUsername() {
		return userId;
	}
	
	// 계정 인증 여부
	public boolean isCertificated() {
		return cert == 'Y' ? true : false; 
	}
	
	// 계정 탈퇴 여부
	public boolean isWithdraw() {
		return withdraw;
	}
	
	// Disabled, 권한 목록
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	// Disabled, 비밀번호
	@Override
	public String getPassword() {
		return null;
	}

	// Disabled, 계정 만료 여부
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	// Disabled, 계정 잠김 여부
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	// Disabled, 비밀번호 만료 여부
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	// Disabled, 계정 활성화 여부
	@Override
	public boolean isEnabled() {
		return false;
	}
}
