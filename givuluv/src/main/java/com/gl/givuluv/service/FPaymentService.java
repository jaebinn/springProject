package com.gl.givuluv.service;

import com.gl.givuluv.domain.dto.FPaymentDTO;

public interface FPaymentService {
	//지금까지 펀딩한 사람수
	int getFundingTotalPeople();
	//지금까지 펀딩금액
	int getFundingTotalCost();
	boolean insertFPayment(FPaymentDTO payment);
	
	FPaymentDTO getLastFPaymentById(String userid);
}
