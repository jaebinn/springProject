package com.gl.givuluv.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.CommentPageDTO;
import com.gl.givuluv.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/comment/*")
public class CommentController {
	@Autowired
	private CommentService service;
	
	@GetMapping("commentList")
	public @ResponseBody List<CommentPageDTO> getMethodName(int cBoardnum, int commentlastnum, Model model, HttpServletRequest req) {
		System.out.println("/comment/commentList");
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		String loginUser = loginUser_temp == null ?  null : loginUser_temp.toString();
		
		return service.getCommentList(cBoardnum, commentlastnum, loginUser);
	}
	
}
