package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.SRegisterDTO;

@Mapper
public interface SRegisterMapper {

	int getNotApproveCnt();
	int getApproveCnt();

	List<SRegisterDTO> getNotApproveList();
	List<SRegisterDTO> getApproveList();
	boolean updatesellerApprove(String sname);
	boolean cancelsellerApprove(String sname);
	SRegisterDTO sellerApproveProfile(String sName);

}
