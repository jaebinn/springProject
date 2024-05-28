package com.gl.givuluv.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.ProductDTO;

@Service
public interface ProductService {
	//리스트 받아오기
	List<ProductDTO> getList();
	
	//insert 하기
	boolean insertP(ProductDTO product);
	
	//delete 하기
	boolean deleteP(int productnum);
	
	//update 하기
	boolean updateP(ProductDTO product);

	String[] getProductConnectid();

	List<ProductDTO> getCategoryList(char pType);
	
}
