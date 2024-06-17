package com.gl.givuluv.service;


import java.util.List;
import java.util.Map;

import com.gl.givuluv.domain.dto.CBoardDTO;
import com.gl.givuluv.domain.dto.FollowDTO;
import com.gl.givuluv.domain.dto.UserDTO;

public interface UserService {
	boolean join(UserDTO user);
	boolean login(String userid, String userpw);
	boolean checkId(String userid);
	boolean UpdateUserInfo(UserDTO user, String loginUser);
	boolean checkNickname(String nickname);
	boolean checkNickAndEmail(String nickname, String email);
	UserDTO getUseridByEmail(String email);
	boolean nickAndIdCheck(String nickname, String userid);
	UserDTO getUserById(String userid);
	String getNicknameByUserId(String userid);
//	보너스 포인트를 불러오기 위해 아이디로 보너스 포인트 불러오기
	String getUserBonusById(UserDTO userid);
//	유저가 기부/펀드한 총 금액을 불러오기 위해 아이디로 s_cost불러오기
	String getUserD_costById(UserDTO userid);
//	유저가 기부한 내역의 수를 불러오기 위함
	int getUserDonationById(UserDTO userid);
//	유저가 펀딩한 내역의 수를 불러오기 위함
	int getUserFundingBiId(UserDTO userid);
//	유저가 기부했던 기부 프로젝트의 기부명을 가져옴 
	String getDonationNameById(UserDTO userid);
//	기부를 행한 일반유저와 매칭되는 댄체/판매자의 이름을 불러오기
	String getDonaUserNameById(UserDTO userid);
//	유저가 기부한 기부 프로젝트의 설립일 불러오기
	String getDoneTimeById(UserDTO userid);
//	유저가 기부한 프로젝트명을 전부 뽑아옴
	List<Map<String, Object>> getDonaNameByUserid(UserDTO userid);
//	우저가 펀딩한 펀딩명을 전부 뽐아옴
	List<String> getFundNameByUserId(UserDTO userid);
//	유저가 펀딩한 프로젝트의 작성자를 불러옴
	List<String> getFundUserNameById(UserDTO userid);
//	유저가 펀딩한 프로젝트의 설립일 불러옴
	List<String> getFundTimeById(UserDTO userid);
//	펀딩제목 가져오기
	List<String> getFundingNameById(UserDTO userid);
	
	String getUserNickNameById(UserDTO userid);
	
	String getUserNameById(UserDTO userid);
	
	String getUserEmailById(UserDTO userid);
	
	String getUserPhoneById(UserDTO userid);
	
	List<String> getd_keyword_reBykeyword(UserDTO keyword);
	
	List<String> getD_boardNumByKeyword_re(List<String> keyword_re);
	
	List<String> getSystemNameByBoardNum(List<String> d_boardNum);
	
	List<String> getD_boardTitleBynum(List<String> d_boardNum);
	
	List<String> getD_boardOrgidBynum(List<String> d_boardNum);
	
	List<String> getF_boardNumByKeyword_re(UserDTO userid);
	
	List<String> getF_boardTitleByNum(List<String> f_boardNum);
	
	List<String> getF_boardOrgidBynum(List<String> f_boardNum);
	
	List<String> getF_boardNumBynum(List<String> f_boardNum);
	
	List<String> getf_keyword_reBykeyword(UserDTO keyword);
	
	List<String> getSystemNameByUserid(UserDTO userid);	
	
	UserDTO getUserInfo(UserDTO userid);
	boolean addFollow(FollowDTO follow);
	
	List<Map<String,Object>> getD_LikeInfoByUserid(String loginUser);
	
	List<CBoardDTO> getLikeInfoByUserid(String loginUser);
	
	Boolean Delete_LikeByUserid(String loginUser);
	
	int SelectC_boardNumByUserid(String loginUser);
	
	int getF_systemNameCntBynum(List<String> f_boardNum);
	
	int getD_systemNameCntBynum(UserDTO userid);
	
	boolean checkuserPw(String userpw);
	
	boolean deleteUser(String loginUser);
	
	boolean deleteF_detail(String loginUser);
	
	boolean deleteD_detail(String loginUser);
	
	boolean deleteFollow(String loginUser);
	
	boolean deleteLike(String loginUser);
	
	boolean deleteReview(String loginUser);
	
	boolean deleteD_payment(String loginUser);
	
	String getuserPwById(String loginUser);
	
	boolean deleteF_payment(String loginUser);
	
	boolean deleteS_payment(String loginUser);
	List<Map<String, Object>> getFundNameByUserid(UserDTO userid);
	String getNicknameById(UserDTO userid);
	List<Map<String, Object>> getRDonaNameByUserid(UserDTO userid);
	List<String> getF_SystemName(UserDTO userid);
	
	List<Map<String, Object>> getF_LikeInfoByUserid(String loginUser);
	
	List<String> getD_systemName(String loginUser);
	
	List<String> getF_systemName(String loginUser);
	
}
