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
	
	int getFollow_amount(String orgid);
	
	int getD_boardnum(String orgid);
	
	String getLike_id(int d_boardnum);
	
	int getLike_amount(int d_boardnum);
	
	int getF_boardnum(String orgid);
	
	String getProduct_name(int f_boardnum);
	
	int getp_amount(int f_boardnum);
	
	int getp_cost(int f_boardnum);
	
	String getReview_id(int f_boardnum);
	
	String getReview_date(int f_boardnum);
	
	int getReview_star(int f_boardnum);
	
	Boolean checkuserPw(String userpw);
	
	Boolean deleteOrg(String orgid);
	
	Boolean deleteFollow(String orgid);
	
	
	Boolean deleteD_Like(int d_boardnum);
	Boolean deleteF_Like(int f_boardnum);
	
	Boolean deleteD_Review(int d_boardnum);
	Boolean deleteF_Review(int f_boardnum);
	
	Boolean deleteD_payment(String orgid);
	
	String getuserPwById(String orgid);
	
	Boolean deleteF_payment(String orgid);
	
	Boolean deleteS_payment(String orgid);
}
