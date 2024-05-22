package com.gl.givuluv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.service.DPaymentService;
import com.gl.givuluv.service.OrgService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/payment/*")
public class PaymentController {
	
	@Autowired
	private DPaymentService pservice;
	@Autowired
	private OrgService oservice;
	
	@PostMapping("confirmPay")
	@ResponseBody
	public void successPayment(@RequestParam int cost, @RequestParam String orgname, @RequestParam int dBoardnum, HttpServletRequest req) {
		DPaymentDTO payment = new DPaymentDTO();
		HttpSession session = req.getSession();
		String userid = (String)session.getAttribute("loginUser");
		payment.setD_cost(cost);
		payment.setOrgid(oservice.getOrgidByOrgname(orgname));
		payment.setUserid(userid);
		payment.setD_boardnum(dBoardnum);
		payment.setType('p');
		System.out.println(payment);
		pservice.insertPayment(payment);
	}
}
	


