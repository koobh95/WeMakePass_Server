package com.github.koobh95.data.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.github.koobh95.data.model.dto.ReplyDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 정보를 갖는 테이블 "reply_tb"와 대응되는 Entity 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@Entity
@Table(name="reply_tb")
@SequenceGenerator(
		name="REPLY_NO_SEQ_GENERATOR",
		sequenceName = "seq_reply_replyNo",
		allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Reply {
	@Id
	@Column(name="reply_no")
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "REPLY_NO_SEQ_GENERATOR")
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

	// 댓글이 작성된 게시글에 대한 정보
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_no", insertable = false, updatable = false)
	private Post post;

	// 댓글을 작성한 유저의 정보
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="writer", insertable = false, updatable = false)
	private User user;

	// 현재 Entity(댓글)의 하위 댓글 목록
	@OneToMany(mappedBy = "parentReplyNo", fetch = FetchType.LAZY)
	private List<Reply> childReplyList;
	
	// DB에 삽입할 Entity 객체를 생성하는 생성자
	public Reply(long parentReplyNo, long postNo, String writer, 
			String content) {
		this.parentReplyNo = parentReplyNo;
		this.postNo = postNo;
		this.writer = writer;
		this.content = content;
		regDate = LocalDateTime.now();
	}
	
	// Entity 객체를 DTO 객체로 변환하여 반환
	public static ReplyDTO toDto(Reply e) {
		return new ReplyDTO(
				e.parentReplyNo != -1,
				e.getReplyNo(),
				e.getWriter(),
				e.getUser().getNickname(),
				e.getContent(),
				e.getRegDate());
	}
}
