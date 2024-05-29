package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class CommentPageDTO {
	private CommentDTO comment;
	private LikeDTO commentLike;
	private int commentLikeCount;
	private String commentFileLink;
	private String commentWriterName;
}
