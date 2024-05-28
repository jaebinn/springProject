package com.gl.givuluv.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.StoreDTO;

@Service
public interface StoreService {
	
	//리스트 가져오기
	StoreDTO getStoreList(String connectid);

	boolean regist(Model model, SBoardDTO sBoard, String sellerId, List<ProductDTO> productList, String filenames, MultipartFile thumbnail) throws Exception;
	
}