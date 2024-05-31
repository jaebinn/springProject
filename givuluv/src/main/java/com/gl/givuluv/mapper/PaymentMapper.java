package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.FPaymentDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.UserDTO;

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

	boolean insertFPayment(FPaymentDTO payment);

	FPaymentDTO getLastFPaymentById(String userid);

	FPaymentDTO getFPaymentByPaymentnum(int paymentnum);

	UserDTO getUserByPaymentnum(int paymentnum);

	ProductDTO getProductByProductnum(int productnum);

	FBoardDTO getFBoardByFBoardnum(int fBoardnum);

	String getOrgnameByOrgid(String orgid);

}
