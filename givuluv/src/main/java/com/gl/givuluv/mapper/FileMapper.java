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

}
