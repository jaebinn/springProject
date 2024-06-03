package com.gl.givuluv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SPaymentDTO;
import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.mapper.PaymentMapper;
import com.gl.givuluv.mapper.ProductMapper;
import com.gl.givuluv.mapper.UserMapper;

@Service
public class SPaymentServiceImpl implements SPaymentService {
	
	@Autowired
	private PaymentMapper pmapper;
	@Autowired
	private ProductMapper prmapper;
	@Autowired
	private UserMapper umapper;
	
	
	@Override
	public boolean insertSPayment(SPaymentDTO s_payment) {
		if(pmapper.insertSPayment(s_payment)) {
			if(prmapper.decreaseAmount(s_payment)) {
				if(umapper.giveBonus(s_payment)) {
					return true;
				}
				else {
					System.out.println("giveBonus:실패");
				}
			}
			else {
				System.out.println("decreaseAmount:실패");
			}
		}
		else {
			System.out.println("insertSPayment:실패");
		}
		return false;
	}
	
	@Override
	public SPaymentDTO getLastSPaymentById(String userid) {
		return pmapper.getLastSPaymentById(userid);
	}
	
	@Override
	public SPaymentDTO getSPaymentByPaymentnum(int paymentnum) {
		return pmapper.getSPaymentByPaymentnum(paymentnum);
	}
	
	@Override
	public UserDTO getUserBySPaymentnum(int paymentnum) {
		return pmapper.getUserBySPaymentnum(paymentnum);
	}
	
	@Override
	public ProductDTO getSProductByProductnum(int productnum) {
		return pmapper.getSProductByProductnum(productnum);
	}
	
	@Override
	public SBoardDTO getSBoardBySBoardnum(int sBoardnum) {
		return pmapper.getSBoardBySBoardnum(sBoardnum);
	}
}
