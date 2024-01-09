package com.github.koobh95.data.model.entity.mapping;

import java.time.LocalDateTime;
import java.util.List;

import com.github.koobh95.data.model.entity.Reply;
import com.github.koobh95.data.model.entity.User;

/**
 *  Post 클래스에서 게시글 목록에 사용할 데이터만 읽어오기 위해서 사용하는 Mapping 클래스다. 게시글의
 * 데이터에서 일반적으로 가장 많은 용량을 차지하는 본문은 게시글 목록을 표시할 때 불필요한 데이터이기 때문에
 * 메모리 낭비를 최소화하기 위해서 이 클래스를 사용한다.
 * 
 * @author BH-Ku
 * @since 2024-01-07
 */
public interface PostMapping {
	long getPostNo();
	String getCategory();
	String getTitle();
	LocalDateTime getRegDate();
	LocalDateTime getDeleteDate();
	long getHit();
	User getUser();
	List<Reply> getReplyList();
}
