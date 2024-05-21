package com.gl.givuluv.service;

import java.util.List;

import com.gl.givuluv.domain.dto.CategoryDTO;
import com.gl.givuluv.domain.dto.Criteria;
import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.domain.dto.OrgDTO;


public interface DBoardService {
	boolean regist(DBoardDTO dboard);
	
	DBoardDTO getDonation(int dBoardnum);
	List<DBoardDTO> getList(); //전체(등록순)
	List<DBoardDTO> getList(CategoryDTO category); //카테고리순
	List<DBoardDTO> getList(Criteria cri);
	List<DBoardDTO> getList(Criteria cri, String orgid);
	
	boolean modify(DBoardDTO dboard);
	boolean changeOrgid(DBoardDTO dboard);
//	boolean addSaveMoney(detail돈, int dBoardnum);
	
	boolean remove(int dBoardnum);
	boolean removeByOrgid(String orgid);

	String getOrgnameBynum(int dBoardnum);
	
	//카테고리 정렬
	List<DBoardDTO> getItemsByCategory(String orgcategory);


}
