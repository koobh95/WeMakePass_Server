package com.github.koobh95.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.koobh95.data.model.dto.JmDTO;
import com.github.koobh95.data.model.entity.Jm;
import com.github.koobh95.data.repository.JmRepository;
import com.github.koobh95.service.JmService;

import lombok.RequiredArgsConstructor;

/**
 * 종목 정보와 관련된 비지니스 로직을 처리한다.
 * 
 * @author BH-Ku
 * @since 2023-12-19
 */
@Service("jmService")
@RequiredArgsConstructor
public class JmServiceImpl implements JmService {
	private final JmRepository jmRepository;
	
	/**
	 *  DB에서 Keyword와 종목 이름이 부분일치하는 데이터를 조회한 결과를 JmDTO로 변환하여 결과를
	 * 반환한다.
	 * 
	 * @param keyword 부분 검색할 검색어
	 * @return List<JmDTO> JmEntity를 JmDTO로 변환한 리스트 
	 */
	@Transactional(readOnly = true)
	@Override
	public List<JmDTO> searchByJmName(String keyword) {
		List<Jm> entityList = jmRepository.findByJmNameContaining(keyword);
		return Jm.toDtoList(entityList);
	}

	/**
	 *  DB에서 Keyword와 종목 이름이 일치하는 데이터를 조회한 결과에서 시험 데이터를 가진 종목에 한해
	 * JmDTO로 변환하여 결과를 반환한다.
	 * 
	 * @param keyword 조회할 종목 이름
	 * @return List<JmDTO> JmEntity를 JmDTO로 변환한 리스트 
	 */
	@Override
	public List<JmDTO> searchByJmNameWithExam(String keyword) {
		List<Jm> entityList = jmRepository.findByJmNameWithExamInfo(keyword);
		return Jm.toDtoList(entityList);
	}
}
