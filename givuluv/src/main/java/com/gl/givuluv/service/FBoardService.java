package com.gl.givuluv.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.UserDTO;


public interface FBoardService {
	boolean regist(Model model, FBoardDTO fBoard, List<ProductDTO> productList, String filenames, MultipartFile thumbnail) throws Exception;

	List<Map<String, Object>> getFundingList();

	Map<String, Object> getFundingDetail(int fBoardnum);

	List<Map<String, Object>> getParticipationRate();

	List<Map<String, Object>> getParticipationCost();

	List<Map<String, Object>> getFundingEnddateList();

	FBoardDTO getFundingByFBoardnum(int fBoardnum);

	UserDTO getUserById(String userid);

	int isApproveOrg(String loginOrg);

	int isApproveOrgX(String loginOrg);

	
}
