package com.gl.givuluv.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.DPaymentDTO;

@Mapper
public interface DPaymentMapper {
	int insertPayment(DPaymentDTO payment);

	int getTotalCostByBoardnum(int dBoardnum);

	int getTotalCostByOrgid(String orgid);

	int getRdonationCntByType(int dBoardnum);
}
