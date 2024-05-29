package com.gl.givuluv.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.QnaDTO;

@Service
public interface QnaService {

	QnaDTO regist(QnaDTO qna);

	List<QnaDTO> getQnaList(int productnum);
	


}
