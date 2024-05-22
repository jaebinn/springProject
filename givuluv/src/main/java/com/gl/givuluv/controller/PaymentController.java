package com.gl.givuluv.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.service.DPaymentService;


@Controller
@RequestMapping("/payment/*")
public class PaymentController {
	
	@Autowired
	private DPaymentService pservice;
	
	@PostMapping("/payDonation")
    public void getPayDonation(@RequestBody DPaymentDTO paymentData) {
        // paymentData 객체에 d_cost, orgname, userid
        System.out.println(paymentData);
        
    }
	

}
