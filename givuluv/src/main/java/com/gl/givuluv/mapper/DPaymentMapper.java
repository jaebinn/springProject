package com.gl.givuluv.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.DPaymentDTO;

@Mapper
public interface DPaymentMapper {

	boolean insertPayment(DPaymentDTO payment);

}
