package com.gl.givuluv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gl.givuluv.domain.dto.OrgapproveDTO;
import com.gl.givuluv.service.OrgapproveService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/orgapp/*")
public class OrgapproveController {
	@Autowired
	private OrgapproveService service;
	
	@PostMapping("/join")
	 public ResponseEntity<Boolean> join(@RequestBody OrgapproveDTO orgapprove, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String orgid = (String)session.getAttribute("loginOrg");
		System.out.println("orgid:"+ orgid);
		System.out.println(orgapprove);
		orgapprove.setOrgid(orgid);
		boolean result = service.join(orgapprove);
       return new ResponseEntity<>(result, HttpStatus.OK);
   }
}
