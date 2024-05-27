package com.gl.givuluv.service;

import java.util.List;

import com.gl.givuluv.domain.dto.CommentDTO;

public interface CommentService {
	boolean registComment(CommentDTO comment);
	
	List<CommentDTO> getComments(int cBoardnum);
}
