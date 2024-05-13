package com.gl.givuluv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.service.UserService;
import com.gl.givuluv.domain.dto.UserDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("login")
    public String login() {
        return "user/login"; 
    }
	@PostMapping("login")
	public String login(String userid, String userpw, HttpServletRequest req) {
		HttpSession session = req.getSession();
		if(service.login(userid, userpw)) {
			session.setAttribute("loginUser", userid);
			return "redirect:/";
		}
		else {
			//
		}
		return "redirect:/";
	}
	
	@GetMapping("idFind")
	public String idFind() {
		return "user/idFind";
	}
	@GetMapping("pwFind")
	public String pwFind() {
		return "user/pwFind";
	}
	
	@GetMapping("join")
	public void replace() {}

	@PostMapping("join")
	public String join(UserDTO user, HttpServletResponse resp) {
		//회원가입 처리
		if(service.join(user)) {
			Cookie cookie = new Cookie("joinid", user.getUserid());
			cookie.setPath("/");
			cookie.setMaxAge(60);
			resp.addCookie(cookie);
		}
		else {
			//
		}
		return "redirect:login";
	}
	
	@GetMapping("checkId")
	@ResponseBody
	public String checkId(String userid) {
		if(service.checkId(userid)) {
			return "O";
		}
		else {
			return "X";
		}
	}	
	
}
