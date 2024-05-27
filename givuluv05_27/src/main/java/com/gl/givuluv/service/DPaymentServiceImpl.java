package com.gl.givuluv.service;

import java.util.List;

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
	@Override
	public boolean insertRPayment(DPaymentDTO payment) {
		return pmapper.insertRPayment(payment);
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
	@Override
	public DPaymentDTO getLastPaymentById(String userid) {
		return pmapper.getLastPaymentById(userid);
	}
	@Override
	public DPaymentDTO getLastRPaymentById(String userid) {
		return pmapper.getLastRPaymentById(userid);
	}
	@Override
	public List<DPaymentDTO> getDPayment() {
		return pmapper.getDPayment();
	}
	@Override
	public int getTodayDonationCost() {
		return pmapper.getTodayDonationCost();
	}
	@Override
	public int getTodayDonationPeople() {
		return pmapper.getTodayDonationPeople();
	}
	
	
}
