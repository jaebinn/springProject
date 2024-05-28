package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class DPaymentDTO {
	private int paymentnum;
	private String userid;
	private String orgid;
	private int dBoardnum;
	private int dCost;
	private String paydate;
	private char type; //일반결제('p') 정기결제('r')
}
