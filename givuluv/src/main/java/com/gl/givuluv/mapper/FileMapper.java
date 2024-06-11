package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.FileDTO;



@Mapper
public interface FileMapper {
	//C
	int insertFile(FileDTO file);
	int insertThumbnail(FileDTO file);
	//R
	FileDTO getFileBySystemname(String systemname);
	List<FileDTO> getFiles(long boardnum);
	//D
	int deleteFileBySystemname(String systemname);
	int deleteFilesByBoardnum(long boardnum);
	List<FileDTO> getFiles();
	FileDTO getFile(FileDTO file);
	List<String> getSystemnameByBoardnum(String dBoardnum);
	FileDTO getFileByProductnum(int productnum);
	FileDTO getFileByStorenum(int storenum);
	List<String> getFileBySellerid(String sellerid);
	List<String> getFileByFBoardnum(int fBoardnum);
	String getOrgProfileByOrgid(String orgid);
	FileDTO getSBoardFile(int connectid);
	List<String> getFileBySBoardnum(int sBoardnum);
	List<String> getFileBySBoardnum(String connectid);
	List<FileDTO> getOrgProfile();
	String getSellerProfileBySName(String sName);
	String getSellerProfileById(String sellerid);
	List<String> getSellerProfileListById(String sellerid);
	FileDTO getFileListBySellerid(String sellerid);
	String getStoreMainImg(int storenum);
	String[] getStoreSubImg(int storenum);
	FileDTO getFileByStorenum(String sellerid);
	int updateFile(FileDTO fdto);

}
