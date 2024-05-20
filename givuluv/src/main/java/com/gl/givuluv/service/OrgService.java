package com.gl.givuluv.service;



import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.OrgDTO;

public interface OrgService {
	boolean join(OrgDTO org, MultipartFile[] files) throws Exception;
	boolean login(String orgid, String orgpw);
	boolean checkId(String orgid);
	String getOrgnameByOrgid(String orgid);
	String getOrgnameBynum(int dBoardnum);
}
