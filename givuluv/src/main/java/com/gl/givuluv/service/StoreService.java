package com.gl.givuluv.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.LikeDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SBoardwithFileDTO;
import com.gl.givuluv.domain.dto.SRegisterDTO;
import com.gl.givuluv.domain.dto.SinfoDTO;
import com.gl.givuluv.domain.dto.StoreDTO;

@Service
public interface StoreService {
	
	//리스트 가져오기
	StoreDTO getStoreList(int connectid);

	boolean regist(Model model, SBoardDTO sBoard, String sellerId, List<ProductDTO> productList, String filenames, MultipartFile thumbnail) throws Exception;
	String getStoreName(int snum);
	
	boolean insertLikeSBoard(LikeDTO likedto);
	
	boolean deleteLikeSBoard(int sboardnum, String userid);
	
	LikeDTO getSBoardLike(int connectid, String loginUser);
	
	// MDM
	boolean checkRegnum(String regnum);

	// MDM
	boolean insertStoreSignup(SRegisterDTO srdto);

	// MDM
	boolean checkStorename(String storename);

	// MDM
	char checkStoreBySellerid(String loginSeller);

	// MDM
	List<SBoardwithFileDTO> getStoreViewProduct(int storenum, String loginUser);

	List<StoreDTO> getStore();
	
	// MDM
	SinfoDTO getSinfoByStorenum(int storenum);

	// MDM
	String getStoreMainImg(int storenum);

	// MDM
	String[] getStoreSubImg(int storenum);

	// MDM
	StoreDTO getStoreByStorenum(int storenum);
}
