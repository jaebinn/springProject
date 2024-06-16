package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.FPaymentDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SPaymentDTO;
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
	
	boolean insertSPayment(SPaymentDTO s_payment);

	SPaymentDTO getLastSPaymentById(String userid);

	SPaymentDTO getSPaymentByPaymentnum(int paymentnum);

	UserDTO getUserBySPaymentnum(int paymentnum);

	SBoardDTO getSBoardBySBoardnum(int sBoardnum);

	ProductDTO getSProductByProductnum(int productnum);

	List<SPaymentDTO> getLastSPaymentBySellerid(String sellerid);

	boolean fundCancelByNum(int paymentnum);
	
	boolean deleteSPayment(String sellerid);
}
