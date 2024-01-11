package com.github.koobh95.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.koobh95.data.model.dto.ReplyDTO;
import com.github.koobh95.data.model.entity.Post;
import com.github.koobh95.data.model.entity.Reply;
import com.github.koobh95.data.model.enums.ErrorCode;
import com.github.koobh95.data.repository.PostRepository;
import com.github.koobh95.data.repository.ReplyRepository;
import com.github.koobh95.exception.ReplyException;
import com.github.koobh95.service.ReplyService;

import lombok.RequiredArgsConstructor;

/**
 * 댓글과 관련된 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2024-01-11
 */
@Service("replyService")
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	private final ReplyRepository replyRepository;
	private final PostRepository postRepository;

	/**
	 * - 특정 게시글의 댓글 목록을 조회한다.
	 * - 댓글을 조회하는 시점에서 게시글이 삭제되었을 수 있으므로 확인한다.
	 * 
	 * @param postNo 조회할 게시글의 고유 식별 번호
	 */
	@Transactional(readOnly = true)
	@Override
	public List<ReplyDTO> replyList(long postNo) {
		Post post = postRepository.findByPostNo(postNo);
		if(post.getDeleteDate() != null)
			throw new ReplyException(
					ErrorCode.REPLY_LOADING_FAILED_POST_DELETED,
					"postNo=" + postNo);
		return convertToReplyDtoList(replyRepository.findByPostNo(postNo));
	}

	/**
	 * - DB에서 읽은 Entity List를 DTO List로 변환하여 반환한다.
	 * - 삭제된 댓글은 추가하지 않는다. 단, 삭제되었지만 하위 댓글이 존재하는 경우 하위 댓글을 표시하기
	 *  위해 객체에 이 댓글은 삭제된 댓글이라는 정보를 추가하고 리스트에 추가한다.
	 * - 댓글을 추가한 뒤 하위 댓글이 존재할 경우 하위 댓글을 추가적으로 읽되 삭제되지 않은 댓글만 추가한다.
	 * 
	 * @param entityList DB에서 읽어온 댓글 목록
	 * @return
	 */
	private List<ReplyDTO> convertToReplyDtoList(List<Reply> entityList) {
		System.out.println("댓글 개수 : " + entityList.size());
		List<ReplyDTO> dtoList = new ArrayList<>();
		
		for(Reply reply : entityList) {
			List<Reply> childReplyList = reply.getChildReplyList();
			
			if(childReplyList.size() == 0) { // 하위 댓글이 없음.
				if(reply.getDeleteDate() != null) // 삭제된 상태
					continue; // 무시
				dtoList.add(Reply.toDto(reply)); // 하위 댓글이 없으니 삽입
				continue;
			} else { // 하위 댓글이 있음.
				ReplyDTO dto = Reply.toDto(reply);
				if(reply.getDeleteDate() != null) // 삭제된 상태
					dto.setDeleted();
				dtoList.add(dto);
			}
			
			// 하위 댓글(답글) 탐색 및 추가
			boolean childExist = false; // 하위 댓글이 모두 삭제되었을 가능성이 있음
			for(Reply childReply : childReplyList) {
				if(childReply.getDeleteDate() == null) {
					dtoList.add(Reply.toDto(childReply));
					childExist = true;
				}
			}
			
			if(!childExist) // 하위 댓글이 모두 삭제된 댓글일 경우 이전에 삽입한 상위 댓글 삭제
				dtoList.remove(dtoList.size()-1);
		}
		
		return dtoList;
	}
}
