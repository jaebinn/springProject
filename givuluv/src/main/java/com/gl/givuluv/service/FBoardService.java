package com.gl.givuluv.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.ProductDTO;


public interface FBoardService {
	boolean regist(Model model, FBoardDTO fBoard, List<ProductDTO> productList, String filenames, MultipartFile thumbnail) throws Exception;

	List<Map<String, Object>> getFundingList();

	Map<String, Object> getFundingDetail(int fBoardnum);

	
}
