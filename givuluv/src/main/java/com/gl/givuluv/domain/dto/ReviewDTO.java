package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class ReviewDTO {
	private int reviewnum;
	private String reviewdetail;
	private String userid;
	private String reviewdate;
	private int star; //펀딩, 가게만
	private int connectid; //게시판번호
	private char type; //'d'기부 'f'펀딩 'm'가게
	
}
