package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class CommentDTO {
	private int commentnum;
	private String commentdetail;
	private String commentregdate;
	private String commentupdatedate;
	private int cBoardnum;
	private String connectid;
	private char type;
}
