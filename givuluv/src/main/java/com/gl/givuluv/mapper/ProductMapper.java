package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.ProductDTO;

@Mapper
public interface ProductMapper {

	String[] getProductConnectid();

	List<ProductDTO> getList();
	List<ProductDTO> getCategoryList(char pType);
	List<ProductDTO> getCategoryList(String category);
	int insertSBoardProduct(ProductDTO product);
	int insertFundingProduct(ProductDTO product);

	ProductDTO getProduct(int productnum);

	List<ProductDTO> getProductByConnectid(int fBoardnum);

}
