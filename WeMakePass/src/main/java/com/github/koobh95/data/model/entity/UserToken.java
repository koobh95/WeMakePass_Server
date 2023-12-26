package com.github.koobh95.data.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자의 인증 정보을 갖는 테이블 "user_token_tb"와 대응되는 Entity 클래스. 
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Entity
@Table(name="user_token_tb")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {
	@Id
	@Column(name="id", unique=true)
	private String userId; // Token의 소유자 (User)ID
	@Column(name="refresh_token", unique=true)
	private String refreshToken; // 발급받은 RefereshToken

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
