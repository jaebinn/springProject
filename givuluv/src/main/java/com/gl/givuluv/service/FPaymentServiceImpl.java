package com.gl.givuluv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.FPaymentDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.mapper.BoardMapper;
import com.gl.givuluv.mapper.PaymentMapper;
import com.gl.givuluv.mapper.ProductMapper;
import com.gl.givuluv.mapper.UserMapper;

@Service
public class FPaymentServiceImpl implements FPaymentService{

	@Autowired
	private PaymentMapper pmapper;
	@Autowired
	private BoardMapper fbmapper;
	@Autowired
	private ProductMapper prmapper;
	@Autowired
	private UserMapper umapper;
	
	@Override
	public int getFundingTotalPeople() {
		return pmapper.getFundingTotalPeople();
	}

	@Override
	public int getFundingTotalCost() {
		return pmapper.getFundingTotalCost();
	}
	@Override
	public boolean insertFPayment(FPaymentDTO payment) {
		if(pmapper.insertFPayment(payment)) {
			prmapper.decreaseAmount(payment);
			fbmapper.updateFSaveMoney(payment.getFBoardnum());
			umapper.updateBonus(payment.getUserid(), (int)(payment.getFCost()*0.1));
			return true;
		}
		return false;
	}

	@Override
	public FPaymentDTO getLastFPaymentById(String userid) {
		return pmapper.getLastFPaymentById(userid);
	}

	@Override
	public FPaymentDTO getFPaymentByPaymentnum(int paymentnum) {
		return pmapper.getFPaymentByPaymentnum(paymentnum);
	}

	@Override
	public UserDTO getUserByPaymentnum(int paymentnum) {
		return pmapper.getUserByPaymentnum(paymentnum);
	}

	@Override
	public ProductDTO getProductByProductnum(int productnum) {
		return pmapper.getProductByProductnum(productnum);
	}

	@Override
	public FBoardDTO getFBoardByFBoardnum(int fBoardnum) {
		return pmapper.getFBoardByFBoardnum(fBoardnum);
	}

	@Override
	public String getOrgnameByOrgid(String orgid) {
		return pmapper.getOrgnameByOrgid(orgid);
	}

	@Override
	public boolean fundCancelByNum(int paymentnum) {
		FPaymentDTO payment = pmapper.getFPaymentByPaymentnum(paymentnum);
		if(pmapper.fundCancelByNum(paymentnum)) {
			prmapper.updateAmount(payment);
			fbmapper.updateFSaveMoney(payment.getFBoardnum());
			umapper.updateBonus(payment.getUserid(), (int)(payment.getFCost()*0.1));
			return true;
		}
		return false;
	}
}
