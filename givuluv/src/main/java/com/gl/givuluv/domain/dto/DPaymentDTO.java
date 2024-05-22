package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class DPaymentDTO {
	int paymentnum;
	String userid;
	String orgid;
	int d_cost;
	char type; //일반결제('p') 정기결제('r')
}
