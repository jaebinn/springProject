package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class SBoardwithFileDTO {
   private ProductDTO product;
   private FileDTO productFile;
   private SBoardDTO sBoard;
   private FileDTO storeFile;
   private String storename;
   private String category;
   
   public void SBoardwithFilesDTO(ProductDTO product, FileDTO productFile, SBoardDTO sBoard, FileDTO storeFile, String storename, String category) {
       this.product = product;
       this.productFile = productFile;
       this.sBoard = sBoard;
       this.storeFile = storeFile;
       this.storename = storename;
       this.category = category;
   }
   
}