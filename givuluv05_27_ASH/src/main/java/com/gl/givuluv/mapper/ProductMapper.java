package com.gl.givuluv.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.ProductDTO;

@Mapper
public interface ProductMapper {
	int insertSBoardProduct(ProductDTO product);
	int insertFundingProduct(ProductDTO product);
}
