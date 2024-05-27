package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.gl.givuluv.domain.dto.CBoardDTO;
import com.gl.givuluv.domain.dto.Criteria;
import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;

@Mapper
public interface BoardMapper {
	//기부 게시글
	int insertDonation(DBoardDTO dboard);
	
	DBoardDTO getDonationByBoardnum(int dBoardnum);
	DBoardDTO getBoardByNum(int dBoardnum);
	
	String getOrgnameBynum(int dBoardnum);
	
	
	int getDonationLastBoardnumByOrgid(String orgid);
	List<DBoardDTO> getListByCategory(String orgcategory);
	List<DBoardDTO> getList();
	List<DBoardDTO> getDonations(Criteria cri);
	List<DBoardDTO> getDonationsByOrgid(String orgid);

	int updateDonation(DBoardDTO dboard);
	int updateDonationOfOrgid(DBoardDTO dboard);
//	int updateDonationOfSaveMoney(detail돈, int dBoardnum);

	int deleteDonationByBoardnum(int dBoardnum);
	int deleteDonationsByOrgid(@Param("orgid")String orgid, @Param("cri")Criteria cri);
	
	//펀딩 게시글
	int insertFunding(FBoardDTO fboard);
	
	FBoardDTO getFundingByBoardnum(int fBoardnum);
	List<FBoardDTO> getFundings(Criteria cri);
	List<FBoardDTO> getFundingsByOrgid(String orgid);
	
	int updateFunding(FBoardDTO fboard);
	int updateFundingOfOrgid(FBoardDTO fboard);
	
	int deleteFundingByBoardnum(int fBoardnum);
	int deleteFundingByOrgid(String orgid);


	String getEnddateByBoardnum(int dBoardnum);
	
	int getFundingLastBoardnumByOrgid(String orgid);
	//가게 게시글
	int insertStoreBoard(SBoardDTO sboard);
	int getStoreBoardLastNumBySNum(int sNum);

	boolean updateSaveMoney(int dBoardnum);
	//캠페인 게시글
	int insertCampaign(CBoardDTO cboard);
	
	int getCampaignLastNumByConnectid(String connectid);
	CBoardDTO getCampaignByCBoardnum(int cBoardnum);
}