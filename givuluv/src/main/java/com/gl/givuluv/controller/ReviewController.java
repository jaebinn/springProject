package com.gl.givuluv.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        int reviewCnt = rservice.getReviewCnt(review.getConnectid());
        System.out.println(nickname);
        ReviewDTO result = rservice.regist(review);
        System.out.println(reviewCnt);
        if(result == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("review", result);
            responseBody.put("nickname", nickname); 
            responseBody.put("reviewCnt", reviewCnt);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
    }
	@GetMapping("delete")
	public String deleteReview(@RequestParam int reviewnum, @RequestParam int connectid) {
		if(rservice.deleteReview(reviewnum)) {
			System.out.println(reviewnum+"번 리뷰삭제");
		}
		else {
			System.out.println("댓글 삭제 실패...");
		}
		return "redirect:/donation/donationView?dBoardnum="+connectid;
	}
	@PutMapping("/{reviewnum}")
	public ResponseEntity<String> updateReview(@RequestBody String updateReview) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(updateReview);

	        // reviewnum과 updatedReviewDetail 추출
	        int reviewnum = jsonNode.get("reviewnum").asInt();
	        String reviewdetail = jsonNode.get("updatedReviewDetail").asText();

	        if (rservice.updateReview(reviewnum, reviewdetail)) {
	            System.out.println("수정");
	            return ResponseEntity.ok("리뷰가 성공적으로 업데이트되었습니다.");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 업데이트에 실패했습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생했습니다.");
	    }
	}

}
