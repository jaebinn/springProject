package com.gl.givuluv.service;

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
	
}
