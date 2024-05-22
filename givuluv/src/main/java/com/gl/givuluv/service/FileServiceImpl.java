package com.gl.givuluv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.mapper.FileMapper;

@Service
public class FileServiceImpl implements FileService{
	@Autowired
	private FileMapper fmapper;

	@Override
	public boolean regist(FileDTO file) {
		return fmapper.insertFile(file) == 1;
	}
	
	
	
	@Override
	public List<String> getSystemnameByBoardnum(String dBoardnum) {
		List<FileDTO> fList = fmapper.getFiles();
		for(FileDTO file : fList) {
			if(file.getConnectionid().equals(dBoardnum+"")) {
				if(file.getType() == 'D') {
					if(file.getBoardthumbnail() == 'Y') {
						return fmapper.getSystemnameByBoardnum(dBoardnum+"");
					}
				}
			}
		}
		return null;
	}
}
