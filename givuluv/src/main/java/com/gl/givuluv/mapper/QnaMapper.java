package com.gl.givuluv.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.QnaDTO;

@Mapper
public interface QnaMapper {

	int insertQna(QnaDTO qna);

	QnaDTO getLastQna(String userid);
	
}
