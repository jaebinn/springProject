package com.gl.givuluv.service;


import com.gl.givuluv.domain.dto.OrgDTO;

public interface OrgService {
	boolean join(OrgDTO org);
	boolean login(String orgid, String orgpw);
	boolean checkId(String orgid);
}
