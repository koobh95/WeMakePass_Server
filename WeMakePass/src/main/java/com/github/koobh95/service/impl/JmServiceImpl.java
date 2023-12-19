package com.github.koobh95.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.koobh95.data.model.dto.JmDTO;
import com.github.koobh95.data.model.entity.JmEntity;
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
	 *  Keyword와 JmName이 부분일치하는 데이터를 DB에서 조회한 후 계열 코드(qualCode)를 제외한
	 * 데이터를 JmDTO에 초기화하여 결과를 반환한다.
	 * 
	 * @param keyword 부분 검색할 검색어
	 * @return List<JmDTO> JmEntity를 JmDTO로 변환한 리스트 
	 */
	@Override
	public List<JmDTO> searchByJmName(String keyword) {
		List<JmEntity> entityList = jmRepository.findByJmNameContaining(keyword);
		return JmEntity.toDtoList(entityList);
	}
}
