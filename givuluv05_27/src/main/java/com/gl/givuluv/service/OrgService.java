package com.gl.givuluv.service;



import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.domain.dto.OrgDTO;

public interface OrgService {
	boolean join(OrgDTO org, MultipartFile[] files) throws Exception;
	boolean login(String orgid, String orgpw);
	boolean checkId(String orgid);
	String getOrgnameByOrgid(String orgid);
	String getOrgnameBynum(int dBoardnum);
	List<String> getOrgnameByCategory(@Param("orgcategory") String orgcategory);
	String getCategoryByOrgid(String orgid);
	String getOrgidByOrgname(String orgname);
}
