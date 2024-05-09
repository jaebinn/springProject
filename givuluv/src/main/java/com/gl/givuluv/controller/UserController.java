package com.gl.givuluv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/*")
public class UserController {
	@GetMapping("login")
    public String login() {
        return "user/login"; // login.html 파일이 위치한 경로
    }

    @GetMapping("kakao")
    public String kakao() {
        return "user/kakao"; // kakao.html 파일이 위치한 경로
    }	
}
