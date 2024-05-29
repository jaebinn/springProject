package com.gl.givuluv.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.StoreDTO;

@Mapper
public interface StoreMapper {
	int insertStore(StoreDTO store);
	
	StoreDTO getStoreBySellerId(String sellerId);
	StoreDTO getStoreBySBoardnum(int sBoarddnum);

	StoreDTO getStoreById(int snum);

	String getStoreName(int snum);
	
}
