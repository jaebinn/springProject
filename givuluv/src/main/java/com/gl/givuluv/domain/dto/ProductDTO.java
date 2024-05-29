package com.gl.givuluv.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductDTO {
	private String productnum;
	private String productname;
	private String pAmount;
	private String cost;
	private String connectid;
	private char pType;

	private List<ProductDTO> productList;
}
