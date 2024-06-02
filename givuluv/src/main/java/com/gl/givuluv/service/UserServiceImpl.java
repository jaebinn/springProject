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

	@Override
	public String getNicknameByUserId(String userid) {
		return umapper.getNicknameByUserId(userid);
	}
//	펀드제목을 불러오기
	@Override
	public List<String> getFundNameByUserId(UserDTO userid) {
		
		return umapper.getFundNameByUserId(userid);
	}
//	펀드 게시판의 글쓴ㅇ이를 불러옴
	@Override
	public List<String> getFundUserNameById(UserDTO userid) {
		
		return umapper.getFundUserNameById(userid);
	}
//	펀드 게시글이 올라온 시간을 불러옴
	@Override
	public List<String> getFundTimeById(UserDTO userid) {
		
		return umapper.getFundTimeById(userid);
	}

	@Override
	public List<String> getFundingNameById(UserDTO userid) {
		
		return umapper.getFundingNameById(userid);
	}

	@Override
	public String getUserNickNameById(UserDTO userid) {
		
		return umapper.getUserNickNameById(userid);
	}

	@Override
	public String getUserNameById(UserDTO userid) {
		
		return umapper.getUserNameById(userid);
	}

	@Override
	public String getUserEmailById(UserDTO userid) {
		
		return umapper.getUserEmailById(userid);
	}

	@Override
	public String getUserPhoneById(UserDTO userid) {
		
		return umapper.getUserPhoneById(userid);
	}

	@Override
	public boolean UpdateUserInfo(UserDTO user, String loginUser) {
		
		return  umapper.UpdateUserInfo(user, loginUser) == 1;
	}

	@Override
	public List<String> getd_keyword_reBykeyword(UserDTO keyword) {

		return umapper.getd_keyword_reBykeyword(keyword);
	}

	@Override
	public List<String> getD_boardNumByKeyword_re(List<String> keyword_re) {
		
		return umapper.getD_boardNumByKeyword_re(keyword_re);
	}

	@Override
	public List<String> getSystemNameByBoardNum(List<String> d_boardNum) {
		
		return umapper.getSystemNameByBoardNum(d_boardNum);
	}

	@Override
	public List<String> getD_boardTitleBynum(List<String> d_boardNum) {
		
		return umapper.getD_boardTitleBynum(d_boardNum);
	}

	@Override
	public List<String> getD_boardOrgidBynum(List<String> d_boardNum) {
		
		return umapper.getD_boardOrgidBynum(d_boardNum);
	}

	@Override
	public List<String> getF_boardNumByKeyword_re(UserDTO userid) {
		
		return umapper.getF_boardNumByKeyword_re(userid);
	}

	@Override
	public List<String> getF_boardTitleByNum(List<String> f_boardNum) {
		
		return umapper.getF_boardTitleByNum(f_boardNum);
	}

	@Override
	public List<String> getF_boardOrgidBynum(List<String> f_boardNum) {
		
		return umapper.getF_boardOrgidBynum(f_boardNum);
	}

	@Override
	public List<String> getF_boardNumBynum(List<String> f_boardNum) {
		
		return umapper.getF_boardNumBynum(f_boardNum);
	}

	@Override
	public List<String> getf_keyword_reBykeyword(UserDTO keyword) {
		
		return umapper.getf_keyword_reBykeyword(keyword);
	}

	@Override
	public List<String> getSystemNameByUserid(UserDTO userid) {
		
		return umapper.getSystemNameByUserid(userid);
	}
}
