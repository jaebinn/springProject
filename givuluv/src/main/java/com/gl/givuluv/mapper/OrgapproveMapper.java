package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gl.givuluv.domain.dto.OrgapproveDTO;

@Mapper
public interface OrgapproveMapper {
    // Create (C)
    int insertOrgapprove(OrgapproveDTO orgapprove);

    // Read (R)
    OrgapproveDTO getOrgapproveByInstituteName(@Param("instituteName") String instituteName);
    List<OrgapproveDTO> getAllOrgapproves();

    // Update (U)
    int updateOrgapprove(OrgapproveDTO orgapprove);

    // Delete (D)
    int deleteOrgapproveByInstituteName(@Param("instituteName") String instituteName);

	int getNotApproveCnt();

	int getApproveCnt();

	List<OrgapproveDTO> getNotApproveList();

	List<OrgapproveDTO> getApproveList();
}