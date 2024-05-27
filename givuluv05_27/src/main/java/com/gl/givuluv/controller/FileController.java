package com.gl.givuluv.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/file/*")
public class FileController {
   
   @Value("${file.dir}")
   private String saveFolder;

   @PostMapping(value="/uploadSummernoteImageFile", produces = "application/json")
   @ResponseBody
   public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile) {
      System.out.println("gson 연결");
      
      Gson gson = new Gson();
      JsonObject jsonObject = new JsonObject();
         
      System.out.println("file 경로에 저장");
      String fileRoot = saveFolder;   //저장될 외부 파일 경로

      System.out.println("확장자 찾기");
      String originalFileName = multipartFile.getOriginalFilename();   //오리지날 파일명
      String extension = originalFileName.substring(originalFileName.lastIndexOf("."));   //파일 확장자
      
      System.out.println("시간관련 랜덤 문자");
      LocalDateTime now = LocalDateTime.now();
      String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            
      System.out.println("저장될 파일명");
      String systemname = time + UUID.randomUUID().toString() + extension;   //저장될 파일 명
      
      System.out.println("파일 위치");
      File targetFile = new File(fileRoot + systemname);
      
      try {
         InputStream fileStream = multipartFile.getInputStream();
         FileUtils.copyInputStreamToFile(fileStream, targetFile);   //파일 저장
         jsonObject.addProperty("url", "/summernoteImage/" + systemname);
         jsonObject.addProperty("responseCode", "success");
            
      } catch (IOException e) {
         FileUtils.deleteQuietly(targetFile);   //저장된 파일 삭제
         jsonObject.addProperty("responseCode", "error");
         e.printStackTrace();
      }
      
      return gson.toJson(jsonObject);
   }
}