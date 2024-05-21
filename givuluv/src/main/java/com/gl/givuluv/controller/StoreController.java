package com.gl.givuluv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store/*")
public class StoreController {
	
	@GetMapping("sBoard")
	public void replace() {}
}
