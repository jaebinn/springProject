package com.gl.givuluv.service;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.domain.dto.OrgDTO;

public interface OrgService {
	boolean join(OrgDTO org, MultipartFile[] files) throws Exception;
	boolean login(String orgid, String orgpw);
	boolean checkId(String orgid);
	boolean checkPw(String orgpw);
	String getOrgnameByOrgid(String orgid);
	String getOrgnameBynum(int dBoardnum);
	List<String> getOrgnameByCategory(@Param("orgcategory") String orgcategory);
	String getCategoryByOrgid(String orgid);
	String getOrgidByOrgname(String orgname);
	int getOrgByUnqnum(int orgunqnum);
	List<Map<String, String>> getOrgProfile();
	boolean checkUnqnumber(int orgunqnum);
	char checkRegisterByOrgid(String loginOrg);
	String getOrgPhoneByOrgid(String orgid);
	
	String getCeoNameByOrgid(String orgid);
	
	String getLogoByOrgid(String orgid);
	
	OrgDTO getOrgInfo(String orgid);
	
	boolean modify(OrgDTO org, MultipartFile files);
	
	String getOrgSystemname(String orgid);
	
	List<String> getD_board(String orgid);
	
	List<String> getF_board(String orgid);
List<String> Dboard_info(String orgid);
	
	String getDboardtitle(String orgid);
	
	String getTarget_amount(String orgid);
	String getf_Target_amount(String orgid);
	
	String getSave_money(String orgid);
	String getf_Save_money(String orgid);
	
	List<String> getD_paymentinfo(String orgid);
	
	List<String> getD_payment_id(String orgid);
	
	List<Integer> getD_payment_cost(String orgid);
	
	String getFboardtitle(String orgid);
	
	String getFollow(String orgid);
	
	String getFollow_amount(String orgid);
	
	String getD_boardnum(String orgid);
	
	String getLike_id(String d_boardnum);
	
	String getLike_amount(String d_boardnum);
	
	String getF_boardnum(String orgid);
	
	String getProduct_name(String f_boardnum);
	
	String getp_amount(String f_boardnum);
	
	String getp_cost(String f_boardnum);
	
	String getReview_id(String f_boardnum);
	
	String getReview_date(String f_boardnum);
	
	String getReview_star(String f_boardnum);
	
	boolean checkuserPw(String userpw);
	
	boolean deleteOrg(String orgid);
	
	boolean deleteFollow(String orgid);
	
	
	boolean deleteD_Like(String d_boardnum);
	boolean deleteF_Like(String f_boardnum);
	
	boolean deleteD_Review(String d_boardnum);
	boolean deleteF_Review(String f_boardnum);
	
	boolean deleteD_payment(String orgid);
	
	String getuserPwById(String orgid);
	
	boolean deleteF_payment(String orgid);
	
	boolean deleteO_approve(String orgid);
	
	boolean deleteO_register(String orgid);
	
	boolean deleteD_board(String orgid);
	
	boolean deleteF_board(String orgid);
	
	List<Map<String, Object>> getD_boardinfo(String orgid);
	
	List<Map<String, Object>> getF_boardinfo(String orgid);
	
	List<Map<String, Object>> getReviewinfo(String f_boardnum);
	
	List<Map<String, Object>> getFollowinfo(String orgid);
	
	List<Map<String, Object>> getLikeinfo(String d_boardnum);
	
	String getLike_cnt(String orgid);
	
	String getReview_cnt(String orgid);
	
	String getFollow_cnt(String orgid);
	
	
}
