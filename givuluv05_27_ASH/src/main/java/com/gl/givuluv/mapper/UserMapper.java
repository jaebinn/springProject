package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.UserDTO;

@Mapper
public interface UserMapper {

	//C
	int insertUser(UserDTO user);
	//R
	UserDTO getUserById(String userid);
	//U
	int updateUser(UserDTO user);
	//D
	int deleteUser(String userid);
	//닉네임 유무
	UserDTO getUserByNickname(String nickname);
	//아이디 찾기
	UserDTO getUseridByEmail(String email);
	//비밀번호 찾기
	boolean nickAndIdCheck(String nickname, String userid);
	
//	유저 아이디로 유저의 보너스 포인트를 불러오기
	String getUserBonusById(UserDTO userid);
	
//	유저 아이디로 유저가 총 후원한 금액을 불러오기
	String getUserD_costById(UserDTO loginUser);
	
//	유저 아이디로 유저가 기부한 횟수를 불러오기
	int getUserDonationById(UserDTO userid);
	
//	유저 아이디로 유저가 펀딩한 횟수를 불러오기
	int getUserFundingById(UserDTO userid);
	
//	유저가 기부한 기부 프로젝트의 이름을 불러오기
	List<String> getDonationNameById(UserDTO userid);
	
//	기부/펀딩 글을 올린 사용자의 이름을 불러옴
	List<String> getDonaUserNameById(UserDTO userid);
	
//	유저가 기부한 기부 프로젝트의 설립일을 불러오기
	List<String> getDoneTimeById(UserDTO userid);
	
//	유저가 기부한 프로젝트명을 전부 뽑아옴
	List<String> getDonaNameByUserId(UserDTO userid);
//	유저가 펀딩한 펀딩명을 전부 뽑아옴
	List<String> getFundNameByUserId(UserDTO userid);
	
	List<String> getFundUserNameById(UserDTO userid);
	
	List<String> getFundTimeById(UserDTO userid);
	
	List<String> getFundingNameById(UserDTO userid);
	
	String getUserNickNameById(UserDTO userid);
	
	String getUserNameById(UserDTO userid);
	
	String getUserEmailById(UserDTO userid);
	
	String getUserPhoneById(UserDTO userid);
	//    
}
