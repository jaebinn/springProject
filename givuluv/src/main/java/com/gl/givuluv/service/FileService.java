package com.gl.givuluv.service;

import java.util.List;

import com.gl.givuluv.domain.dto.FileDTO;

public interface FileService {
	boolean regist(FileDTO file);

	List<String> getSystemnameByBoardnum(String dBoardnum);
	
	FileDTO getFileByProductnum(int productnum);

	FileDTO getFileByStorenum(int snum);
	
	FileDTO getSBoardFile(int connectid);

	List<String> getSellerProfile(String sellerid);

	FileDTO getFileBySellerID(String sellerid);
}
