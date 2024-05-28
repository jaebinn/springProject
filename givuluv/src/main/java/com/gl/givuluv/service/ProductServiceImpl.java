package com.gl.givuluv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductMapper pmapper; 
	
	@Override
	public List<ProductDTO> getList() {
		return pmapper.getList();
	}
	
	@Override
	public List<ProductDTO> getCategoryList(char pType) {
		return pmapper.getCategoryList(pType);
	}
	
	@Override
	public boolean deleteP(int productnum) {
		return false;
	}

	
	@Override
	public boolean insertP(ProductDTO product) {
		return false;
	}
	
	@Override
	public boolean updateP(ProductDTO product) {
		return false;
	}
	
	@Override
	public String[] getProductConnectid() {
		return pmapper.getProductConnectid();
	}
	
	
}
