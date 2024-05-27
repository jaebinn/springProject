package com.gl.givuluv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.CommentDTO;
import com.gl.givuluv.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentMapper cmmapper;
	
	@Override
	public boolean registComment(CommentDTO comment) {
		return cmmapper.insertComment(comment) == 1;
	}

	@Override
	public List<CommentDTO> getComments(int cBoardnum) {
		return cmmapper.getCommentsByCBoardnum(cBoardnum);
	}

}
