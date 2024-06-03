package com.gl.givuluv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.OrgapproveDTO;
import com.gl.givuluv.mapper.OrgapproveMapper;
@Service
public class OrgapproveServicelmpl implements OrgapproveService {

	@Autowired
	private OrgapproveMapper orgamapper;

	@Override
	public boolean join(OrgapproveDTO orgapprove) {
		return orgamapper.insertOrgapprove(orgapprove) == 1;
	}

	@Override
	public OrgapproveDTO getOrgapproveByInstituteName(String instituteName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrgapproveDTO> getAllOrgapproves() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
