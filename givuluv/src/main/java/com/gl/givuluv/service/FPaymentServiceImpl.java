package com.gl.givuluv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.mapper.PaymentMapper;

@Service
public class FPaymentServiceImpl implements FPaymentService{

	@Autowired
	private PaymentMapper pmapper;
	
	@Override
	public int getFundingTotalPeople() {
		return pmapper.getFundingTotalPeople();
	}

	@Override
	public int getFundingTotalCost() {
		return pmapper.getFundingTotalCost();
	}

}
