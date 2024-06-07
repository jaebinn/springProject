package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.SRegisterDTO;
import com.gl.givuluv.domain.dto.SinfoDTO;

@Mapper
public interface SinfoMapper {

	SinfoDTO getSinfoByStorenum(int storenum);


}
