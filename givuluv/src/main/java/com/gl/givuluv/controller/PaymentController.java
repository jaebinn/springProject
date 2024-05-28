package com.gl.givuluv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.service.DBoardService;
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
	@Autowired
	private DBoardService dbservice;
	
	@PostMapping("confirmPay")
	@ResponseBody
	public DPaymentDTO successPayment(@RequestParam int cost, @RequestParam String orgname, @RequestParam int dBoardnum, HttpServletRequest req) {
		DPaymentDTO dpayment = new DPaymentDTO();
		HttpSession session = req.getSession();
		String userid = (String)session.getAttribute("loginUser");
		dpayment.setDCost(cost);
		dpayment.setOrgid(oservice.getOrgidByOrgname(orgname));
		dpayment.setUserid(userid);
		dpayment.setDBoardnum(dBoardnum);
		dpayment.setType('p');
		System.out.println(dpayment);
		System.out.println(pservice.getTotalCostByBoardnum(dBoardnum));
		
		if(pservice.insertPayment(dpayment)) {
			dbservice.updateSaveMoney(dBoardnum);
			DPaymentDTO payment = pservice.getLastPaymentById(userid);
			System.out.println(payment);
			return payment;
		}
		return null;
	
	}
	@PostMapping("RconfirmPay")
	@ResponseBody
	public DPaymentDTO successRPayment(@RequestParam int cost, @RequestParam String orgname, @RequestParam int dBoardnum, HttpServletRequest req) {
		DPaymentDTO dpayment = new DPaymentDTO();
		HttpSession session = req.getSession();
		String userid = (String)session.getAttribute("loginUser");
		dpayment.setDCost(cost);
		dpayment.setOrgid(oservice.getOrgidByOrgname(orgname));
		dpayment.setUserid(userid);
		dpayment.setDBoardnum(dBoardnum);
		dpayment.setType('r');
		if(pservice.insertRPayment(dpayment)) {
			DPaymentDTO payment = pservice.getLastRPaymentById(userid);
			return payment;
		}
		return null;
		
	}
}
	


