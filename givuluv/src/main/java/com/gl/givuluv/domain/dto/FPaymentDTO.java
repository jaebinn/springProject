package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class FPaymentDTO {
	private int paymentnum;
	private String userid;
	private String orgid;
	private int FBoardnum;
	private int amount;
	private int FCost;
	private int productnum;
	private String paydate;
	private String reqetc;
}

