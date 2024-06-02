package com.gl.givuluv.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.service.MailSendService;
import com.gl.givuluv.service.OrgService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/org/*")
public class OrgController {
	
	@Autowired
	private OrgService service;
	
	@GetMapping("join")
	public String orgjoin() {
		return "org/join";
	}
	
	@PostMapping("join")
	public String join(OrgDTO org, MultipartFile[] files, HttpServletResponse resp) throws Exception {
		//회원가입 처리
		
		System.out.println("OrgController에서 찍힌 파일 : "+files);
		
		if(service.join(org, files)) {
			System.out.println("org회원가입성공!");
			Cookie cookie = new Cookie("joinid", org.getOrgid());
			cookie.setPath("/");
			cookie.setMaxAge(60);
			resp.addCookie(cookie);
		}
		else {
			//
		}
		return "user/login";
	}
	
	@PostMapping("login")
	public String login(String orgid, String orgpw, HttpServletRequest req) {
	    HttpSession session = req.getSession();
	    if(service.login(orgid, orgpw)) {
	        System.out.println(orgid + " 로그인됨");
	        session.setAttribute("loginOrg", orgid);
	        return "redirect:/";
	    } else {
	        System.out.println("로그인 실패");
	        return "user/login";
	    }
	}
	
	
	@GetMapping("checkId")
	@ResponseBody
	public String checkId(String orgid) {
		if(service.checkId(orgid)) {
			return "O";
		}
		else {
			return "X";
		}
	}
	
	@GetMapping("register")
	public String register() {
		return "org/register";
	}
	@GetMapping("logsignup")
	public String logsignup() {
		return "org/logsignup";
	}
	@GetMapping("groupinfo")
	 public String groupinfo() {
	    return "org/groupinfo";
	}
	 @GetMapping("signupcomplete")
	 public String signupcomplete() {
	    return "org/signupcomplete";
	}
	 @GetMapping("checkunqnumber")
		@ResponseBody
		public String checkunqnum(int orgunqnum) {
		 System.out.println(service.checkUnqnumber(orgunqnum));
		 	if(service.checkUnqnumber(orgunqnum)) {
	            return "X";
	        }
	        else {
	            return "O";
	        }
		}
	//인덱스 org프로필
	@GetMapping("getOrgProfile")
	@ResponseBody
	public List<Map<String, String>> getOrgProfile(){
		List<Map<String, String>> orgprofile = service.getOrgProfile();
		System.out.println("뭐야 "+orgprofile);
		return orgprofile;
	}
}
