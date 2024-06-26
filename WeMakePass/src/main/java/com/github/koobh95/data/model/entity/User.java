package com.github.koobh95.data.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Post> postList; // 사용자가 작성한 게시글 목록, 사용하지 않음.
	
	/**
	 *  DB에 추가할 새로운 User 객체를 생성할 때 사용되는 메서드로서 필수 정보들을 제외한 모든 데이터는
	 * 기본값으로 설정한다. 대부분의 속성들은 default 값이 모두 정해져 있지만 명시적으로 보기 위하여 
	 * 모든 필드에 값을 모두 초기화하였다.
	 * 
	 * @param userId 아이디
	 * @param password 단방향 암호화된 비밀번호
	 * @param nickname 닉네임
	 * @param email 이메일
	 * @return
	 */
	public static User createNewUser(String userId, String password, 
			String nickname, String email) {
		User user = new User();
		user.userId = userId;
		user.password = password;
		user.nickname = nickname;
		user.email = email;
		user.lastPassword = null;
		user.role = "USER";
		user.regDate = LocalDateTime.now();
		user.withdrawDate = null;
		user.cert = 'N';
		return user;
	}
	
	// 계정 인증에 성공했을 때 계정 인증 여부를 갱신
	public void setCertificated() {
		cert = 'Y';
	}
	
	/**
	 *  사용자 계정의 비밀번호를 갱신한다. 현재 사용 중인 비밀번호를 마지막 사용한 비밀번호에 저장한 뒤
	 * 새로운 비밀번호를 현재 계정의 비밀번호로 사용한다.
	 * 
	 * @param newPassword 새로운 비밀번호
	 */
	public void updatePassword(String newPassword) {
		lastPassword = password;
		password = newPassword;
	}
	
	/**
	 * 닉네임을 갱신한다.
	 * 
	 * @param newNickname 변경할 닉네임
	 */
	public void updateNickname(String newNickname) {
		nickname = newNickname;
	}
}
