package com.gl.givuluv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.service.OrgService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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
	public String join(OrgDTO org, HttpServletResponse resp) {
		//회원가입 처리
		if(service.join(org)) {
			Cookie cookie = new Cookie("joinid", org.getOrgid());
			cookie.setPath("/");
			cookie.setMaxAge(60);
			resp.addCookie(cookie);
		}
		else {
			//
		}
		return "org/login";
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
}
