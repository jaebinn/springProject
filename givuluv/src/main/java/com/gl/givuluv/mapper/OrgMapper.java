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
}
