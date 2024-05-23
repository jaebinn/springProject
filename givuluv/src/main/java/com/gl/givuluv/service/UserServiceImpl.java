package com.gl.givuluv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper umapper;
	
	@Override
	public boolean join(UserDTO user) {
		return umapper.insertUser(user) == 1;
	}

	public boolean login(String userid, String userpw) {
		UserDTO user = umapper.getUserById(userid);
		if(user != null) {
			if(user.getUserpw().equals(userpw)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkId(String userid) {
		return umapper.getUserById(userid) == null;
	}

	@Override
	public boolean checkNickname(String nickname) {
		return umapper.getUserByNickname(nickname) == null;
	}

	@Override
	public boolean checkNickAndEmail(String nickname, String email) {
		UserDTO user = umapper.getUserByNickname(nickname);
		if(user != null) {
			if(user.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public UserDTO getUseridByEmail(String email) {
		return umapper.getUseridByEmail(email);
	}

	@Override
	public boolean nickAndIdCheck(String nickname, String userid) {
		UserDTO user = umapper.getUserByNickname(nickname);
		if(user != null) {
			if(user.getUserid().equals(userid)) {
				return true;
			}			
		}
		return false;
		
	}

	@Override
	public UserDTO getUserById(String userid) {
		return umapper.getUserById(userid);
	}
	
//	유저 아이디로 유저의 보너스 포인트를 불러오기
	@Override
	public String getUserBonusById(UserDTO userid) {
		
		return umapper.getUserBonusById(userid);
	}
	
//	유저 아이디로  유저가 총 후원한 금액을 불러오기
	@Override
	public String getUserD_costById(UserDTO userid){
		
		return umapper.getUserD_costById(userid);
	}
	
	//유저가 기부한 내역의 수를 불러오기 위함
	@Override
	public int getUserDonationById(UserDTO userid) {
		
		return umapper.getUserDonationById(userid);
	}

//	유저가 펀딩한 내역의 수를 불러오기 위함
	@Override
	public int getUserFundingBiId(UserDTO userid) {
		
		return umapper.getUserFundingById(userid);
	}

//	유저가 기부한 기부프로젝트의 기부명을 불러옴
	@Override
	public String getDonationNameById(UserDTO userid) {
		
		return umapper.getDonationNameById(userid);
	}
//	유저가 참여한 기부 프로젝트의 담당자 이름을 불러옴
	@Override
	public String getDonaUserNameById(UserDTO userid) {
		
		return umapper.getDonaUserNameById(userid);
	}

//	유저가 기부한 기부 프로젝트의 설립일 불러오기
	@Override
	public String getDoneTimeById(UserDTO userid) {
		
		return umapper.getDoneTimeById(userid);
	}

//	유저가 기부한 프로젝트명을 전부 뽑아옴
	@Override
	public List<String> getDonaNameByUserid(UserDTO userid) {
		
		return umapper.getDonaNameByUserId(userid);
	}

}
