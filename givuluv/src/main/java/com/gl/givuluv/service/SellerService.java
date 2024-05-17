package com.gl.givuluv.service;

import com.gl.givuluv.domain.dto.SellerDTO;

public interface SellerService {

//	회원가입 
	boolean join(SellerDTO seller);
//	로그인
	boolean login(String sellerid, String sellerpw);
//	이메일로 아이디 찾기
	boolean checkIdAndEmail(String sellerid, String email);
//	아이디 중복확인
	boolean checkId_duplication(String sellerid);
	
//	로그인(비밀번호 확인)
	String checkPw(String sellerpw);
//	로그인(아이디 확인)
	String checkId(String sellerid);
}
