package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.SRegisterDTO;
import com.gl.givuluv.domain.dto.StoreDTO;

@Mapper
public interface StoreMapper {
	int insertStore(StoreDTO store);
	
	StoreDTO getStoreBySellerId(String sellerId);
	StoreDTO getStoreBySBoardnum(int sBoarddnum);

	StoreDTO getStoreById(int snum);

	String getStoreName(int snum);

	List<StoreDTO> getMStoreList();

	StoreDTO getStoreBySBoardnum(String connectid);
	
	StoreDTO getStoreBySName(String sName);
	
	int updateStore(StoreDTO store, String sellerid);

	int checkRegnum(String regnum);

	int insertStoreSignup(SRegisterDTO srdto);

	int checkStorename(String storename);

	char checkStoreBySellerid(String loginSeller);
	
	int storeInfoCheck(String sellerid);

	List<StoreDTO> getStoreListBySNum(int sNum);

}
