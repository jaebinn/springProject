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
}
