package com.gl.givuluv.service;

import com.gl.givuluv.domain.dto.DPaymentDTO;

public interface DPaymentService {

	boolean insertPayment(DPaymentDTO payment);
    //현재까지 기부금액
	int getTotalCostByBoardnum(int dBoardnum);
    //총 모금액
	int getTotalCostByOrgid(String orgid);
	//정기기부자 수
	int getRdonationCntByType(int dBoardnum);

}
