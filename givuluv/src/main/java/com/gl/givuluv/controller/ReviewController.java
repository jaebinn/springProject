package com.gl.givuluv.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gl.givuluv.domain.dto.ReviewDTO;
import com.gl.givuluv.service.ReviewService;
import com.gl.givuluv.service.UserService;

@Controller
@RequestMapping("/review/*")
public class ReviewController {
	
	@Autowired
	private ReviewService rservice;
	@Autowired
	private UserService uservice;
	
	@PostMapping(value="regist", consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<Map<String, Object>> regist(@RequestBody ReviewDTO review){
        System.out.println(review);
        String nickname = uservice.getNicknameByUserId(review.getUserid());
        System.out.println(nickname);
        ReviewDTO result = rservice.regist(review);
        
        if(result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("review", result);
            responseBody.put("nickname", nickname); 
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
    }
}
