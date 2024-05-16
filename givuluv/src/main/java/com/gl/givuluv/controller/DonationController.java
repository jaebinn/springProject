package com.gl.givuluv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donation/*")
public class DonationController {
	
	@GetMapping("dBoard")
	public String dBoard() {
		return "donation/dBoard";
	}
}
