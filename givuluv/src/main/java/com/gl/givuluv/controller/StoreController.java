package com.gl.givuluv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/store/*")
public class StoreController {
	
	@GetMapping("sBoard")
	public void replace() {}
	
	   @GetMapping("write")
	   public void write() {}
	   
	   @PostMapping("write")
	   public String regist(Model model, SBoardDTO sBoard, String sellerId, @ModelAttribute("productList") ProductDTO productList, String filenames, MultipartFile thumbnail, HttpServletRequest req) throws Exception {
	      //경로
	      System.out.println("Post : store/write");
	      //파라미터 출력
	      System.out.println(sBoard.getSTitle());
	      System.out.println(sBoard.getSContent());
	      System.out.println(sellerId);
	      for (ProductDTO product : productList.getProductList()) {
	         System.out.println(product.getProductname());
	         System.out.println(product.getCost());
	         System.out.println(product.getPAmount());
	      }
	      System.out.println(filenames);
	      
	      //세션 검사
	      //가게 세션 가져오기 > 해당하는 가게num 있는지 확인하기 > 가게num이랑 파라미터num이랑 비교하기 > 로직구성하기 
	      //가게 등록 만들어지면 만들기
	      
	      
	      System.out.println("실패");
	      return "store/sBoard";
	   }
}
