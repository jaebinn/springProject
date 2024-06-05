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
   private LikeDTO like;
   
   public void SBoardwithFilesDTO(ProductDTO product, FileDTO productFile, SBoardDTO sBoard, FileDTO storeFile, String storename, String category,LikeDTO like) {
       this.product = product;
       this.productFile = productFile;
       this.sBoard = sBoard;
       this.storeFile = storeFile;
       this.storename = storename;
       this.category = category;
       this.like = like;
   }
   
   public void SBoardwithFilesDTO(ProductDTO product, FileDTO productFile, SBoardDTO sBoard, LikeDTO like) {
	   this.product = product;
       this.productFile = productFile;
       this.sBoard = sBoard;
       this.like = like;
   }
}