package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.OrgDTO;

@Mapper
public interface OrgMapper {
		//C
		int insertOrg(OrgDTO user);
		//R
		OrgDTO getOrgById(String userid);
		//U
		int updateOrg(OrgDTO org);
		//D
		int deleteOrg(String orgid);
		String getOrgnameByOrgid(String orgid);
		List<String> getOrgnameByCategory(String orgcategory);
		String getCategoryByOrgid(String orgid);
		String getOrgidByOrgname(String orgname);
		int getOrgByUnqnum(int orgunqnum);
		List<String> getOrgnameListByOrgid(String connectionid);
		boolean checkUnqnumber(int orgunqnum);
		OrgDTO checkPw(String pw);
		char checkRegisterByOrgid(String loginOrg);
		
		String getOrgPhoneByOrgid(String orgid);
		String getCeoNameByOrgid(String orgid);
		String getLogoByOrgid(String orgid);
		
		OrgDTO getOrgInfo(String orgid);
		
		String getOrgSystemname(String orgid);
		
		List<String> getD_board(String orgid);
		
		List<String> getF_board(String orgid);
		int modify(OrgDTO org);
List<String> getDboard_info(String orgid);
		
		String getDboardtitle(String orgid);
		
		String getTarget_amount(String orgid);
		
		String getSave_money(String orgid);
		
		List<String> getD_paymentinfo(String orgid);
		
		List<String> getD_payment_id(String orgid);
		
		List<Integer> get_D_payment_cost(String orgid);
		
		String getFboardtitle(String orgid);
		
		String getf_Target_amount(String orgid);
		
		String getf_Save_money(String orgid);
		
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
		
		
		int deleteFollow(String orgid);
		int deleteD_Like(int d_boardnum); //connecid
		int deleteF_Like(int f_boardnum); //connecid
		int deleteD_Review(int d_boardnum); //connectid
		int deleteF_Review(int f_boardnum); //connectid
		int deleteD_payment(String orgid);
		int deleteF_payment(String orgid);
}
