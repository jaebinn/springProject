package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class SBoardwithFileDTO {
	private ProductDTO product;
	private FileDTO productFile;
	private StoreDTO store;
	private FileDTO storeFile;
	private String category;
	
	public void SBoardwithFilesDTO(ProductDTO product, FileDTO productFile, StoreDTO store, FileDTO storeFile, String category) {
	    this.product = product;
	    this.productFile = productFile;
	    this.store = store;
	    this.storeFile = storeFile;
	    this.category = category;
	}
	
}
