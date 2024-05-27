package com.gl.givuluv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.CBoardDTO;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.mapper.BoardMapper;
import com.gl.givuluv.mapper.FileMapper;

@Service
public class CBoardServiceImpl implements CBoardService {
	@Autowired
	private BoardMapper bmapper;

	@Autowired
	private FileMapper fmapper;

	@Override
	public boolean regist(CBoardDTO cboard, String filenames) {
		if (bmapper.insertCampaign(cboard) == 1) {
			int cBoardnum = bmapper.getCampaignLastNumByConnectid(cboard.getConnectid());
			String[] filenameList = filenames.split(",");
			if (!filenameList[0].isEmpty()) {
				for (String systemname : filenameList) {
					FileDTO file = new FileDTO();
					file.setConnectionid(cBoardnum + "");
					file.setType('C');
					file.setSystemname(systemname);
					fmapper.insertFile(file);
				}
			}
			System.out.println("regist cboard 성공");
			return true;
		}
		System.out.println("regist cboard 실패");
		return false;
	}

	@Override
	public int getCampaignLastNumByConnectid(String connectid) {
		return bmapper.getCampaignLastNumByConnectid(connectid);
	}

	@Override
	public CBoardDTO getCampaign(int cBoardnum) {
		return bmapper.getCampaignByCBoardnum(cBoardnum);
	}

}
