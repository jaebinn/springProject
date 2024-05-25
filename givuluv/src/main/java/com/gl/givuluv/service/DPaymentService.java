package com.gl.givuluv.service;

import java.util.List;

import com.gl.givuluv.domain.dto.DPaymentDTO;

public interface DPaymentService {
	//일반결제 db삽입
	boolean insertPayment(DPaymentDTO payment);
	//정기결제 db삽입 
	boolean insertRPayment(DPaymentDTO payment);
    //현재까지 기부금액
	int getTotalCostByBoardnum(int dBoardnum);
    //총 모금액
	int getTotalCostByOrgid(String orgid);
	//정기기부자 수
	int getRdonationCntByType(int dBoardnum);
	//방금 삽입한 dto가져오기
	DPaymentDTO getLastPaymentById(String userid);
	DPaymentDTO getLastRPaymentById(String userid);
	
	List<DPaymentDTO> getDPayment();
	int getTodayDonationCost();
	int getTodayDonationPeople();
	int getDonationTotalPeople();
	int getDonationTotalCost();
	

}
