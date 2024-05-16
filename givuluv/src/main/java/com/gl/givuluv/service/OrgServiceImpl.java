package com.gl.givuluv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.mapper.OrgMapper;

@Service
public class OrgServiceImpl implements OrgService{

	@Autowired
	private OrgMapper omapper;
	
	@Override
	public boolean join(OrgDTO org) {
		return omapper.insertOrg(org) == 1;
	}

	@Override
	public boolean login(String orgid, String orgpw) {
		OrgDTO org = omapper.getOrgById(orgid);
		if(org != null) {
			if(org.getOrgpw().equals(orgpw)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkId(String orgid) {
		return omapper.getOrgById(orgid) == null;
	}
}
