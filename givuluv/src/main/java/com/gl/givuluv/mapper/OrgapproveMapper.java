package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.domain.dto.OrgapproveDTO;
import com.gl.givuluv.domain.dto.SRegisterDTO;

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

	boolean updateorgApprove(int approvenum);

	boolean cancelorgApprove(int approvenum);

	OrgapproveDTO orgApproveProfile(int oapprovenum);

	int isApproveOrg(String loginOrg);

	int isApproveOrgX(String loginOrg);

	

}