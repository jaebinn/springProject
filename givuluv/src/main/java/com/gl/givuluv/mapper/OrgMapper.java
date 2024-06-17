package com.gl.givuluv.mapper;

import java.util.List;
import java.util.Map;

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
		
		
		int deleteFollow(String orgid);
		int deleteD_Like(String d_boardnum); //connecid
		int deleteF_Like(String f_boardnum); //connecid
		int deleteD_Review(String d_boardnum); //connectid
		int deleteF_Review(String f_boardnum); //connectid
		int deleteD_payment(String orgid);
		int deleteF_payment(String orgid);
		
		boolean deleteO_approve(String orgid);
		
		boolean deleteO_register(String orgid);
		
		boolean deleteD_board(String orgid);
		
		boolean deleteF_board(String orgid);
		int checkuserPw(String orgpw);
		
		List<Map<String, Object>> getD_boardinfo(String orgid);
		
		List<Map<String, Object>> getF_boardinfo(String orgid);
		
		List<Map<String, Object>> getReviewinfo(String f_boardnum);
		
		List<Map<String, Object>> getFollowinfo(String orgid);
		
		List<Map<String, Object>> getLikeinfo(String d_boardnum);
		
		String getLike_cnt(String orgid);
		
		String getReview_cnt(String orgid);
		
		String getFollow_cnt(String orgid);
		
		
}
