package com.gl.givuluv.service;

import java.util.List;

import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.FPaymentDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.UserDTO;

public interface FPaymentService {
	//지금까지 펀딩한 사람수
	int getFundingTotalPeople();
	//지금까지 펀딩금액
	int getFundingTotalCost();
	boolean insertFPayment(FPaymentDTO payment);
	
	FPaymentDTO getLastFPaymentById(String userid);
	FPaymentDTO getFPaymentByPaymentnum(int paymentnum);
	UserDTO getUserByPaymentnum(int paymentnum);
	ProductDTO getProductByProductnum(int productnum);
	FBoardDTO getFBoardByFBoardnum(int fBoardnum);
	String getOrgnameByOrgid(String orgid);
}
