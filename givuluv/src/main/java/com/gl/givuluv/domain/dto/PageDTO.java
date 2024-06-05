package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class PageDTO {
	private int startPage;
	private int endPage;
	private int realEnd;
	private long total;
	private boolean prev, next;
	private Criteria cri;
	
	public PageDTO(long total, Criteria cri) {
		int pagenum = cri.getPagenum();
		int amount = cri.getAmount();
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int) Math.ceil(pagenum / 10.0) * 10;
		this.startPage = this.endPage - 9;
		this.realEnd = (int) Math.ceil(total*1.0 / amount);
		this.realEnd = this.realEnd == 0 ? 1 : this.realEnd;
		this.endPage = endPage > realEnd ? realEnd : endPage;
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < this.realEnd;
	}
	public PageDTO(long total, Criteria cri, String sellerid) {
		int pagenum = cri.getPagenum();
		int amount = cri.getAmount();
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int) Math.ceil(pagenum / 10.0) * 10;
		this.startPage = this.endPage - 9;
		this.realEnd = (int) Math.ceil(total*1.0 / amount);
		this.realEnd = this.realEnd == 0 ? 1 : this.realEnd;
		this.endPage = endPage > realEnd ? realEnd : endPage;
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < this.realEnd;
	}
}







