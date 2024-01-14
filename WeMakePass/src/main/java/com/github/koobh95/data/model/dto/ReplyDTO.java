package com.github.koobh95.data.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Reply Entity 클래스의 멤버 변수 중 댓글 게시에 필요한 데이터로만 재구성한 DTO 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-11
 */
@Getter
@Setter
@NoArgsConstructor
public class ReplyDTO {
	private long replyNo; // 댓글의 고유 식별 번호
	private String writerId; // 작성자 아이디
	private String writerNickname; // 작성자 닉네임
	private String content; // 댓글 내용
	@JsonFormat(shape = JsonFormat.Shape.STRING, 
			pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime regDate; // 댓글 작성 날짜
	private boolean reReply; // 하위 댓글인지 여부
	private boolean deleted; // 삭제된 댓글이지만 하위 댓글이 남아 있는 경우 true
	
	// 부모 댓글 객체를 생성할 때 사용하는 생성자
	public ReplyDTO(long replyNo, String writerId, String writerNickname, 
			String content, LocalDateTime regDate) {
		reReply = false;
		deleted = false;
		this.replyNo = replyNo;
		this.writerId = writerId;
		this.writerNickname = writerNickname;
		this.content = content;
		this.regDate = regDate;
	}

	// 대댓글 객체를 생성할 때 사용하는 생성자
	public ReplyDTO(boolean reReply, long replyNo, String writerId, 
			String writerNickname, String content, LocalDateTime regDate) {
		deleted = false;
		this.reReply = reReply;
		this.replyNo = replyNo;
		this.writerId = writerId;
		this.writerNickname = writerNickname;
		this.content = content;
		this.regDate = regDate;
	}
	
	/**
	 *  삭제된 댓글일 경우 삭제되었다는 표시 외에 클라이언트로 넘겨도 유의미한 데이터가 없으므로 삭제되었다는
	 * 데이터만 저장한 객체를 반환한다.
	 * 
	 * @return
	 */
	public static ReplyDTO ofDeletedReply() {
		ReplyDTO repltDto = new ReplyDTO();
		repltDto.deleted = true;
		return repltDto;
	}
}
