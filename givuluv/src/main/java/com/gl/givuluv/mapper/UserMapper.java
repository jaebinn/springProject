package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.CBoardDTO;
import com.gl.givuluv.domain.dto.FollowDTO;
import com.gl.givuluv.domain.dto.SPaymentDTO;
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
	String getDonationNameById(UserDTO userid);
	
//	기부/펀딩 글을 올린 사용자의 이름을 불러옴
	String getDonaUserNameById(UserDTO userid);
	
//	유저가 기부한 기부 프로젝트의 설립일을 불러오기
	String getDoneTimeById(UserDTO userid);
	
//	유저가 기부한 프로젝트명을 전부 뽑아옴
	List<String> getDonaNameByUserId(UserDTO userid);
	String getNicknameByUserId(String userid);
	
//	유저와 단체의 팔로우
	FollowDTO getfollow(FollowDTO follow);

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
	
	int UpdateUserInfo(UserDTO user, String loginUser);
	
	List<String> getSearch_reBykeyword(UserDTO keyword);
	
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
	
	void updateBonus(String userid, int bonus);
	
	boolean giveBonus(SPaymentDTO s_payment);
	
	UserDTO getUserInfo(UserDTO userid);
	//  유저와 단체 follow 삽입
	int insertFollow(FollowDTO follow);
	  
	//  유저의 카테고리
	String getUserCategoryById(String userid);

List<CBoardDTO> getLikeInfoByUserid(String loginUser);
	
	int Delete_LikeByUserid(String loginUser);
	
	int SelectC_boardNumByUserid(String loginUser);
	
	int getF_systemNameCntBynum(List<String> f_boardNum);
	
	int getD_systemNameCntBynum(UserDTO userid);
	
	int checkuserPw(String userpw);
	
	int deleteF_detail(String loginUser);
	
	int deleteD_detail(String loginUser);
	
	int deleteFollow(String loginUser);
	
	int deleteLike(String loginUser);
	
	int deleteReview(String loginUser);
	
	int deleteD_payment(String loginUser);
	
	String getuserPwById(String loginUser);
	
	int deleteF_payment(String loginUser);
	
	int deleteS_payment(String loginUser);
}
