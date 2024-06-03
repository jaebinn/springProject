package com.gl.givuluv.service;

import java.util.List;

import com.gl.givuluv.domain.dto.OrgapproveDTO;

public interface OrgapproveService {
	
	boolean join(OrgapproveDTO orgapprove);
    OrgapproveDTO getOrgapproveByInstituteName(String instituteName);
    List<OrgapproveDTO> getAllOrgapproves();
    
}
