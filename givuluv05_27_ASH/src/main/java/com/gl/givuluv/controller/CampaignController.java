package com.gl.givuluv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/campaign/*")
public class CampaignController {
	
	@GetMapping("cBoard")
	public String cBoardList(Model model) {
		return "campaign/cBoard";
	}
	
	
}
