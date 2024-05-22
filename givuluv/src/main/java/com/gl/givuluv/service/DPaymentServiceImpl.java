package com.gl.givuluv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.mapper.DPaymentMapper;

@Service
public class DPaymentServiceImpl implements DPaymentService{
	
	@Autowired
	private DPaymentMapper pmapper;
	@Override
	public boolean insertPayment(DPaymentDTO payment) {
		return pmapper.insertPayment(payment);
	}
	
}
