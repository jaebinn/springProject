package com.gl.givuluv.mapper;


import java.util.List;


import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.CommentDTO;

@Mapper
public interface CommentMapper {
	int insert(CommentDTO comment);
	
	CommentDTO getLastComment(CommentDTO comment);
	int getCommentLastNum();
	
	List<CommentDTO> getComments(int cBoardnum, int commentnum, int amount);
	
	int update(CommentDTO comment);
	int delete(int commentnum);
}
