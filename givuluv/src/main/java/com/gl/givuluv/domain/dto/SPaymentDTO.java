package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class SPaymentDTO {
	private int paymentnum;
	private String userid;
	private String sellerid;
	private int SBoardnum;
	private int amount;
	private int SCost;
	private int productnum;
	private String paydate;
	private String reqetc;
}

