package com.gl.givuluv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.SellerDTO;

import com.gl.givuluv.service.MailSendService;
import com.gl.givuluv.service.SellerService;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller/*")

public class SellerController {

	@Autowired
	private SellerService service;
	private final MailSendService mailService;
	
	@Autowired 
	public SellerController(MailSendService mailService) {
		this.mailService = mailService;
	}
	//판매자 회원가입 =================================================================
	
	//get방식 일때 (기존 UserController 방식을 인용했습니다) 
	@GetMapping("join")
	public void replace() {}
	
	//post방식 일때 (기존 UserController 방식을 인용했습니다) 
	@PostMapping("join")
	public String join(SellerDTO seller, HttpServletResponse resp) {
		//회원가입 처리
		if(service.join(seller)) {
			Cookie cookie = new Cookie("join_sellerid", seller.getSellerid());
			cookie.setPath("/");
			cookie.setMaxAge(60);
			resp.addCookie(cookie);
		}
		else {
			//
		}
		return "user/login";
	}
	//판매자 회원가입 끝 ================================================================
	
	//이메일 인증 =====================================================================
	//이메일 인증(기존 UserController 방식을 인용했습니다)
		@GetMapping("checkIdAndEmail")
		@ResponseBody
		//판매자는 닉네임이 없어서 'checkNickAndEmail' 을 'checkIdAndEmail'로 변경 후 SellerService에 추가하였습니다.
		public ResponseEntity<String> mailCheck(@RequestParam String sellerid, @RequestParam String email) {
			if(service.checkIdAndEmail(sellerid, email)) {
				System.out.println("이메일 인증 요청이 들어옴!");
				System.out.println("이메일 인증 이메일 : " + email);
				String result = mailService.joinEmail(email);
	            return ResponseEntity.ok(result);			
			} 
			else {
				System.out.println("일치하는 [판매자] 회원이 없습니다.");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 판매자 회원이 없습니다.");		
			}
		}
	//이메일 인증 끝 ==================================================================
			
	//아이디 중복검사 =================================================================
		//아이디 중복검사
		@GetMapping("checkId")
		@ResponseBody
		public String checkId(String sellerid) {
			if(service.checkId_duplication(sellerid)) {
				System.out.println("O");
				return "O";
			}
			else { 
				System.out.println("X");
				return "X";
			}
		}	
	//아이디 중복검사 끝 ==============================================================
	
	//로그인 시작 ===================================================================
		@GetMapping("login")
	    public String login() {
	        return "user/login"; 
	    }
		@GetMapping("logout")
		public String logout(HttpServletRequest req) {
			req.getSession().invalidate();
			return "redirect:/";
		}
		@PostMapping("login")
		public String login(String sellerid, String sellerpw, HttpServletRequest req) {
		    HttpSession session = req.getSession();
		    if(service.login(sellerid, sellerpw)) {
		    	System.out.println(sellerid);
		    	System.out.println(sellerpw);
		        System.out.println(sellerid + " 로그인됨");
		        session.setAttribute("loginSeller", sellerid);
		        return "redirect:/";
		    } else {
		    	System.out.println(sellerid);
		    	System.out.println(sellerpw);
		        System.out.println("로그인 실패");
		        return "user/login";
		    }
		}
		 @GetMapping("my/home")
	      public String SellerMyHome(String sellerid, HttpServletRequest req) {
	         HttpSession session = req.getSession();
	         return "seller/my/home";
	      }
	      
	      @GetMapping("my/storeList")
	      public String SellerMyStoreList(String sellerid, HttpServletRequest req) {
	         HttpSession session = req.getSession();
	         return "seller/my/storeList";
	      }
	      
	      @GetMapping("my/storeUpdate")
	      public String SellerMyStoreUpdate(String sellerid, HttpServletRequest req) {
	         HttpSession session = req.getSession();
	         return "seller/my/storeUpdate";
	      }
	//로그인 끝 ====================================================================
}
