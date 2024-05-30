package com.gl.givuluv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.domain.dto.FPaymentDTO;
import com.gl.givuluv.mapper.BoardMapper;
import com.gl.givuluv.mapper.PaymentMapper;

@Service
public class FPaymentServiceImpl implements FPaymentService{

	@Autowired
	private PaymentMapper pmapper;
	@Autowired
	private BoardMapper fbmapper;
	
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
			fbmapper.updateFSaveMoney(payment.getFBoardnum());
			return true;
		}
		return false;
	}

	@Override
	public FPaymentDTO getLastFPaymentById(String userid) {
		return pmapper.getLastFPaymentById(userid);
	}
}
