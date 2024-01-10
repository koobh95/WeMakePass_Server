package com.github.koobh95.data.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

/**
 * 댓글 정보를 갖는 테이블 "reply_tb"와 대응되는 Entity 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@Entity
@Table(name="reply_tb")
@Getter
public class Reply {
	@Id
	@Column(name="reply_no")
	private long replyNo; // 댓글 고유 식별 번호
	@Column(name="parent_reply_no")
	private long parentReplyNo; // 이 댓글이 답글일 경우 상위 댓글의 replyNo를 가짐.
	@Column(name="post_no")
	private long postNo; // 댓글이 작성된 게시글의 고유 식별 번호
	private String writer; // 댓글 작성자의 아이디
	private String content; // 댓글 내용
	@Column(name="reg_date")
	private LocalDateTime regDate; // 댓글 작성 날짜
	@Column(name="delete_date")
	private LocalDateTime deleteDate; // 댓글이 삭제되었을 경우 댓글 삭제 날짜

	// 댓글이 작성된 게시글에 대한 정보, 사용하지 않으므로 LAZY로 설정
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_no", insertable = false, updatable = false)
	private Post post;

	// 댓글을 작성한 유저의 정보, 닉네임을 즉시 읽어야 하므로 EAGER로 설정
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="writer", insertable = false, updatable = false)
	private User user;
}