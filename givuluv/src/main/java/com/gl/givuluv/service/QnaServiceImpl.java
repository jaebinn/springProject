package com.gl.givuluv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.QnaDTO;
import com.gl.givuluv.mapper.QnaMapper;

@Service
public class QnaServiceImpl implements QnaService {
	
	@Autowired
	private QnaMapper qmapper;
	
	@Override
	public QnaDTO regist(QnaDTO qna) {
		if(qmapper.insertQna(qna) == 1) {
			return qmapper.getLastQna(qna.getUserid());
		}
		return null;
	}
	
	@Override
	public List<QnaDTO> getQnaList(int productnum) {
		return qmapper.getQnaList(productnum);
	}
	
	@Override
	public boolean deleteQna(int qnanum) {
		return qmapper.deleteQna(qnanum);
	}
	
	@Override
	public QnaDTO modify(QnaDTO qna) {
		if(qmapper.updateQna(qna) == 1) {
			return qmapper.getModifyQna(qna.getQnanum());
		}
		return null;
	}
}
