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
	public List<ProductDTO> getCategoryList(String category) {
		return pmapper.getCategoryList(category);
	}

	@Override
	public ProductDTO getProduct(int productnum) {
		return pmapper.getProduct(productnum);
	}
	
	
}
