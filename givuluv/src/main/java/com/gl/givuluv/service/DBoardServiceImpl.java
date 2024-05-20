package com.gl.givuluv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.CategoryDTO;
import com.gl.givuluv.domain.dto.Criteria;
import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.mapper.BoardMapper;

@Service
public class DBoardServiceImpl implements DBoardService{

	@Autowired
	private BoardMapper dbmapper;
	@Override
	public boolean regist(DBoardDTO dboard) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DBoardDTO getDonation(int dBoardnum) {
		return dbmapper.getDonationByBoardnum(dBoardnum);
	}

	@Override
	//전체 
	public List<DBoardDTO> getList() {
		return dbmapper.getList();
	}

	@Override
	//카테고리순
	public List<DBoardDTO> getList(CategoryDTO category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DBoardDTO> getList(Criteria cri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DBoardDTO> getList(Criteria cri, String orgid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modify(DBoardDTO dboard) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeOrgid(DBoardDTO dboard) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int dBoardnum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeByOrgid(String orgid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getOrgnameBynum(int dBoardnum) {
		return dbmapper.getOrgnameBynum(dBoardnum);
	}


}
