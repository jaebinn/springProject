package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.QnaDTO;

@Mapper
public interface QnaMapper {

	int insertQna(QnaDTO qna);

	QnaDTO getLastQna(String userid);
	
	List<QnaDTO> getQnaList(int productnum);

	boolean deleteQna(int qnanum);

	int updateQna(QnaDTO qna);

	QnaDTO getModifyQna(int qnanum);
	
	List<QnaDTO> getNoAnswerList(String productnum);

}