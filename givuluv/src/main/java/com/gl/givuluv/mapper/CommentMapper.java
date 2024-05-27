package com.gl.givuluv.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.CommentDTO;

@Mapper
public interface CommentMapper {
	int insertComment(CommentDTO comment);
	
	List<CommentDTO> getCommentsByCBoardnum(int cBoardnum);
}
