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
		return pmapper.insertPayment(payment) == 1;
	}
	@Override
	public int getTotalCostByBoardnum(int dBoardnum) {
		return pmapper.getTotalCostByBoardnum(dBoardnum);
	}
	@Override
	public int getTotalCostByOrgid(String orgid) {
		return pmapper.getTotalCostByOrgid(orgid);
	}
	@Override
	public int getRdonationCntByType(int dBoardnum) {
		return pmapper.getRdonationCntByType(dBoardnum);
	}
	
}
