package com.gl.givuluv.service;

import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SPaymentDTO;
import com.gl.givuluv.domain.dto.UserDTO;

@Service
public interface SPaymentService {

	boolean insertSPayment(SPaymentDTO s_payment);

	SPaymentDTO getLastSPaymentById(String userid);

	SPaymentDTO getSPaymentByPaymentnum(int paymentnum);

	UserDTO getUserBySPaymentnum(int paymentnum);

	ProductDTO getSProductByProductnum(int productnum);
	
	SBoardDTO getSBoardBySBoardnum(int sBoardnum);


	

}
