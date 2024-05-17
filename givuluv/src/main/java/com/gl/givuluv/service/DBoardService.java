package com.gl.givuluv.service;

import java.util.List;

import com.gl.givuluv.domain.dto.Criteria;
import com.gl.givuluv.domain.dto.DBoardDTO;

public interface DBoardService {
	boolean regist(DBoardDTO dboard);
	
	DBoardDTO getDonation(int dBoardnum);
	List<DBoardDTO> getList(Criteria cri);
	List<DBoardDTO> getList(Criteria cri, String orgid);
	
	boolean modify(DBoardDTO dboard);
	boolean changeOrgid(DBoardDTO dboard);
//	boolean addSaveMoney(detail돈, int dBoardnum);
	
	boolean remove(int dBoardnum);
	boolean removeByOrgid(String orgid);
}
