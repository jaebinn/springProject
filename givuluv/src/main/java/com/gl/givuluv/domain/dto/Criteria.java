package com.gl.givuluv.domain.dto;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
//페이지를 구성하는 기준
public class Criteria {
	private int pagenum;
	private int amount;
	private String keyword;
	private int startrow;
	
	public Criteria() {
		this(1,10);
	}
	public Criteria(int pagenum, int amount) {
		this.pagenum = pagenum;
		this.amount = amount;
		this.startrow = (this.pagenum - 1) * this.amount;
	}
	
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
		this.startrow = (this.pagenum - 1) * this.amount;
	}
	
	
	public String getListLink() {							// ? 앞에 붙는 URI 문자열
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pagenum", pagenum)
				.queryParam("amount", amount)
				.queryParam("keyword", keyword);
		
		return builder.toUriString();
	}
}








