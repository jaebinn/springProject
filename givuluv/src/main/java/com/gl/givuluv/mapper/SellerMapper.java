package com.gl.givuluv.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.SellerDTO;

@Mapper
public interface SellerMapper {
	//회원가입 시 받은 판매자 정보를 DB에 넣기
	//성공 시 ture == 1 이기에 int형 
	int insertSeller(SellerDTO seller);
	
	//아이디 중복검사를 위해 저장된 아이디값을 불러오기
	//boolean 타입
	SellerDTO getSellerById_duplication(String sellerid);
	
	//판매자 정보가 업데이트 되었을 때 저장된 데이터 수정
	//성공 시 ture == 1 이기에 int형 
	int updateSeller(SellerDTO seller);
	
	//판매자가 회원 탈퇴 시 저장된 정보 삭제
	int deleteSeller(String sellerid);

	//로그인을 위해 비밀번호 불러오기
	String getSellerByPw(String sellerpw);
	//로그인을 위해 아이디 불러오기
	SellerDTO getSellerById(String sellerid);
	
	String getCategory(int snum);
}
