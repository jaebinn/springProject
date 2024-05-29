package com.gl.givuluv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.SellerDTO;
import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.mapper.SellerMapper;

@Service
public class SellerServicempl implements SellerService{
	
	@Autowired
	private SellerMapper sellmapper;

	@Override
	public boolean join(SellerDTO seller) {
		
		return sellmapper.insertSeller(seller) == 1;
	}

	@Override
	public boolean checkIdAndEmail(String sellerid, String email) {
		SellerDTO seller = sellmapper.getSellerById_duplication(sellerid);
		if(sellerid != null) {
			if(seller.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkId_duplication(String sellerid) {
		
		return sellmapper.getSellerById_duplication(sellerid) == null;
	}
	
//	boolean 타입이 아님 pw를 받아와야 함
	@Override
	public String checkPw(String sellerpw) {
		
		return sellmapper.getSellerByPw(sellerpw) ;
	}

	
	@Override
	public boolean login(String sellerid, String sellerpw) {
		SellerDTO user = sellmapper.getSellerById(sellerid);
		String user_pw = sellmapper.getSellerByPw(sellerpw);
		if(user != null) {
			System.out.println(user.getSellerpw());
			System.out.println(sellerpw);
			if(user_pw.equals(sellerpw)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String checkId(String sellerid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getCategory(int snum) {
		return sellmapper.getCategory(snum);
	}
	
}
