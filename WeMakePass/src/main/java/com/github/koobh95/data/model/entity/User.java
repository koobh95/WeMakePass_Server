package com.github.koobh95.data.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보를 갖는 테이블 "user_tb"와 대응되는 Entity 클래스.
 * 
 * @author BH-Ku
 * @since 2023-12-23
 */
@Entity
@Table(name="user_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
	@Id
	@Column(name="id")
	private String userId; // 유저 아이디
	private String password; // 유저 비밀번호(암호화)
	@Column(name="last_password")
	private String lastPassword; // 마지막 사용된 비밀번호
	private String email; // 인증 이메일
	private String nickname; // 닉네임
	private String role; // 유저 권한(USER, ADMIN)
	@Column(name="reg_date")
	private LocalDateTime regDate; // 가입 날짜, 시간
	@Column(name="withdraw_date")
	private LocalDateTime withdrawDate; // 계정 탈퇴 시간
	private char cert; // 이메일 인증 여부
}
