package com.github.koobh95.data.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *  사용자가 이메일 인증을 시도했을 때 인증 코드와 관련 데이터를 저장할 목적으로 생성된 테이블 
 * "user_cert_tb"과 대응되는 Entity 클래스다.
 * 
 * @author BH-Ku
 * @since 2023-12-30
 */
@Entity
@Table(name="user_cert_tb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCert {
	@Id
	@Column(name = "id")
	private String userId; // 인증 코드를 요청한 유저의 ID
	private String code; // 인증 코드
	@Column(name = "send_time")
	private LocalDateTime sendTime; // 메일 발송 시간
	
	// 인증 코드를 갱신하고 메일 발송 시간을 최신화한다.
	public void updateCode(String code) {
		this.code = code;
		sendTime = LocalDateTime.now();
	}
}
