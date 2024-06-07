package com.gl.givuluv.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.ProductDTO;

@Service
public interface ProductService {
	//리스트 받아오기
	List<ProductDTO> getList();
	
	ProductDTO getSList(int connectid);
	
	//insert 하기
	boolean insertP(ProductDTO product);
	
	//delete 하기
	boolean deleteP(int productnum);
	
	//update 하기
	boolean updateP(ProductDTO product);
	
	List<Map<String, Object>> getMProductList();

	int getProductnumByNameAndConnectid(String productname, int fBoardnum);
	
	List<ProductDTO> getProduct(int productnum);
	
	//MDM 수정
	int[] getMConnectid();
	
	//MDM 수정
	int[] getMConnectidByCategory(String category);

	int getProductnumBySBoardnum(int sBoardnum);
	
	int getSProductnumByNameAndConnectid(String productname, int sBoardnum);

}
