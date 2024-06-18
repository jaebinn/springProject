package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.Criteria;
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

	List<QnaDTO> getQnaListWithCri(Criteria cri, int int1);

	List<QnaDTO> getNoAnswerQnaListWithCri(Criteria cri, int int1);

	long getTotalByProductnum(String productnum);

	long getNoAnswerTotalByProductnum(String productnum);

	boolean updateQnaAnswer(QnaDTO qna);

}