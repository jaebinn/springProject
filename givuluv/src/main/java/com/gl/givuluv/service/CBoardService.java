package com.gl.givuluv.service;

import com.gl.givuluv.domain.dto.CBoardDTO;

public interface CBoardService {
	boolean regist(CBoardDTO cboard, String filenames);
	
	int getCampaignLastNumByConnectid(String connectid);
	CBoardDTO getCampaign(int cBoardnum);
	
}
