package com.gl.givuluv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.domain.dto.FPaymentDTO;
import com.gl.givuluv.service.DBoardService;
import com.gl.givuluv.service.DPaymentService;
import com.gl.givuluv.service.FPaymentService;
import com.gl.givuluv.service.OrgService;
import com.gl.givuluv.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/payment/*")
public class PaymentController {
	
	@Autowired
	private DPaymentService pservice;
	@Autowired
	private FPaymentService fpservice;
	@Autowired
	private ProductService prservice;
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
			dbservice.updateSaveMoney(dBoardnum);
			DPaymentDTO payment = pservice.getLastRPaymentById(userid);
			return payment;
		}
		return null;
		
	}
	
	@PostMapping("confirmFunding")
	@ResponseBody
	public FPaymentDTO successFunding(@RequestParam int cost, @RequestParam String orgname,
			@RequestParam int fBoardnum, @RequestParam String productname, @RequestParam int amount,
			@RequestParam String ordermemo, HttpServletRequest req) {
		FPaymentDTO payment = new FPaymentDTO();
		HttpSession session = req.getSession();
		String userid = (String)session.getAttribute("loginUser");
		if(ordermemo.equals("basic")) {
			ordermemo = "없음";
		}
		else if(ordermemo.equals("door")) {
			ordermemo = "문 앞에 놓아주세요.";
		}
		else if(ordermemo.equals("missed")) {
			ordermemo = "부재시 연락 부탁드려요.";
		}
		else if(ordermemo.equals("advance")) {
			ordermemo = "배송 전 미리 연락해 주세요.";
		}
		payment.setFBoardnum(fBoardnum);
		payment.setOrgid(oservice.getOrgidByOrgname(orgname));
		payment.setUserid(userid);
		payment.setFCost(cost);
		payment.setAmount(amount);
		payment.setReqetc(ordermemo);
		payment.setProductnum(prservice.getProductnumByNameAndConnectid(productname, fBoardnum));
		if(fpservice.insertFPayment(payment)) {
			FPaymentDTO fpayment = fpservice.getLastFPaymentById(userid);
			System.out.println(fpayment);
			return fpayment;
		}
		return null;
	}
	
	
}
	


