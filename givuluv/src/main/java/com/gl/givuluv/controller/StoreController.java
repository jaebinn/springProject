package com.gl.givuluv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.service.SBoardService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/store/*")
public class StoreController {
	
	@Autowired
	private SBoardService sservice;
	
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
	         List<ProductDTO> products = productList.getProductList(); 
	         for (ProductDTO product : products) {
	            System.out.println(product.getProductname());
	            System.out.println(product.getCost());
	            System.out.println(product.getPAmount());
	         }
	         System.out.println(filenames);
	         // 세션 검사
	         HttpSession session = req.getSession();
	         String sessionSellerId = session.getAttribute("loginSeller").toString();
	         if(sessionSellerId.equals(sellerId)) {
	            if(sservice.regist(model, sBoard, sellerId, products, filenames, thumbnail)) {
	               System.out.println("성공"); 
	            }
	            
	         }
	         System.out.println("실패");
	         return "store/sBoard";
	      }
}
