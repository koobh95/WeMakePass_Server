package com.github.koobh95.data.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.domain.Page;

import com.github.koobh95.data.model.dto.PostDTO;
import com.github.koobh95.data.model.entity.mapping.PostMapping;

import lombok.Getter;

/**
 * 게시글 정보를 갖는 테이블 "post_tb"와 대응되는 Entity 클래스.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
@Entity
@Table(name="post_tb")
@Getter
public class Post {
	@Id
	@Column(name="post_no")
	private long postNo; // 게시글의 고유 식별 번호
	@Column(name="board_no")
	private long boardNo; // 게시글이 작성된 게시판의 고유 식별 번호
	private String category; // 게시글의 카테고리
	private String writer; // 게시글 작성자의 ID
	private String title; // 게시글 제목
	private String content; // 게시글 내용
	@Column(name="reg_date")
	private LocalDateTime regDate; // 게시글 작성 날짜
	@Column(name="delete_date")
	private LocalDateTime deleteDate; // 게시글이 삭제되었을 경우 삭제된 날자
	private long hit; // 게시글 조회수
	
	// 게시글 작성자의 닉네임을 얻어오기 위해 사용되며, 게시글을 읽을 때 즉시 사용되므로 EAGER로 설정.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="writer", insertable = false, updatable = false)
	private User user;
	
	// 댓글 목록. 게시글 조회 시 댓글 수를 읽어야 하므로 EAGER로 설정.
	@OneToMany(mappedBy="post", fetch = FetchType.EAGER)
	private List<Reply> replyList = new ArrayList<>();
	
	// Page 객체가 가진 Post Entity 리스트를 Post DTO 리스트로 변환하여 반환한다.
	public static Page<PostDTO> toDtoList(Page<PostMapping> page){
		return page.map(m -> 
			PostDTO.builder()
				.postNo(m.getPostNo())
				.category(m.getCategory())
				.title(m.getTitle())
				.nickname(m.getUser().getNickname())
				.regDate(m.getRegDate())
				.hit(m.getHit())
				.replyCount(getReplyCount(m.getReplyList()))
				.build());
	}
	
	// 게시글에 작성된 댓글의 수를 반환.
	private static int getReplyCount(List<Reply> replyList) {
		int count = 0;
		for(Reply e : replyList)
			if(e.getDeleteDate() == null)
				count++;
		return count;
	}
}
