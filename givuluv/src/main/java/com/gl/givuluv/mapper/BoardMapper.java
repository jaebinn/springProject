package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.DBoardDTO;

@Mapper
public interface BoardMapper {
   int insertDonation(DBoardDTO dboard);
   
   DBoardDTO getDonationByBoardnum(int dBoardnum);
   List<DBoardDTO> getDonationsByOrgid(String orgid);
   
   int deleteDonationByBoardnum(int dBoarddnum);
   int deleteDonationsByOrgid(String orgid);   
}