package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.DPaymentDTO;

@Mapper
public interface PaymentMapper {
	boolean insertPayment(DPaymentDTO payment);

	boolean insertRPayment(DPaymentDTO payment);
	
	int getTotalCostByBoardnum(int dBoardnum);

	int getTotalCostByOrgid(String orgid);

	int getRdonationCntByType(int dBoardnum);

	DPaymentDTO getLastPaymentById(String userid);

	DPaymentDTO getLastRPaymentById(String userid);

	List<DPaymentDTO> getDPayment();

	int getTodayDonationCost();

	int getTodayDonationPeople();

	int getDonationTotalPeople();

	int getDonationTotalCost();
	
	int getFundingTotalPeople();

	int getFundingTotalCost();

}
