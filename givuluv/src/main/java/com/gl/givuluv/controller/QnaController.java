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
import com.gl.givuluv.domain.dto.QnaDTO;
import com.gl.givuluv.service.QnaService;

@Controller
@RequestMapping("/qna/*")
public class QnaController {
	
	@Autowired
	private QnaService qservice;
	
	@PostMapping(value="regist", consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<QnaDTO> regist(@RequestBody QnaDTO qna){
        QnaDTO result = qservice.regist(qna);
        
        if(result == null) {
        	return new ResponseEntity<QnaDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
			return new ResponseEntity<QnaDTO>(result,HttpStatus.OK);
        }
    }
	
	@PostMapping(value="modify", consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<QnaDTO> modify(@RequestBody QnaDTO qna){
		QnaDTO result = qservice.modify(qna);
		
		if(result == null) {
			return new ResponseEntity<QnaDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			return new ResponseEntity<QnaDTO>(result,HttpStatus.OK);
		}
	}
	
	@GetMapping("delete")
	public String deleteQna(@RequestParam int qnanum, @RequestParam int productnum) {
		if(qservice.deleteQna(qnanum)) {
			System.out.println(qnanum+"번 Q&A 삭제");
		}
		else {
			System.out.println(qnanum+"번 Q&A 삭제 실패");
		}
		
		return "redirect:/store/productView?productnum="+productnum;
	}
	

}
