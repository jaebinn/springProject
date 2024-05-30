package com.gl.givuluv.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.CommentDTO;
import com.gl.givuluv.domain.dto.CommentPageDTO;
import com.gl.givuluv.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/comment/*")
public class CommentController {
	@Autowired
	private CommentService service;
	
	@GetMapping("commentList")
	public ResponseEntity<List<CommentPageDTO>> getMethodName(int cBoardnum, int commentlastnum, HttpServletRequest req) {
		System.out.println("/comment/commentList");
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		String loginUser = loginUser_temp == null ?  null : loginUser_temp.toString();
		return new ResponseEntity<List<CommentPageDTO>>(service.getCommentList(cBoardnum, commentlastnum, loginUser), HttpStatus.OK);
	}
	
	@PostMapping(value="regist", consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<CommentDTO> getMethodName(@RequestBody CommentDTO comment, HttpServletRequest req) {
		if(service.commentSpaceCheck(comment.getCommentdetail())) {
			return new ResponseEntity<CommentDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println("/comment/commentRegist");
		System.out.println(comment);
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		Object loginOrg_temp = session.getAttribute("loginOrg");
		if(loginUser_temp != null) {
			System.out.println("로그인 user : "+loginUser_temp);
			comment.setType('U'); 
			comment.setConnectid(loginUser_temp.toString());
		}
		else if(loginOrg_temp != null) {
			System.out.println("로그인 org : "+loginUser_temp);
			comment.setType('O');
			comment.setConnectid(loginOrg_temp.toString());
		}
		else {
			System.out.println("실패");
			System.out.println("로그인 user : "+loginUser_temp);
			System.out.println("로그인 org : "+loginUser_temp);
			return new ResponseEntity<CommentDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println("session 검사 완료");

		CommentDTO result = service.registComment(comment);
		System.out.println("댓글 등록 완료 : "+result);
		return result == null ?
				new ResponseEntity<CommentDTO>(HttpStatus.INTERNAL_SERVER_ERROR) :
				new ResponseEntity<CommentDTO>(result, HttpStatus.OK);
	}
	
	@PatchMapping(value="{commentnum}", consumes = "application/json")
	public ResponseEntity<String> modify(@RequestBody CommentDTO comment){
		return service.modify(comment) ?
				new ResponseEntity<String>(HttpStatus.OK) :
					new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value="{commentnum}")
	public ResponseEntity<String> remove(@PathVariable("commentnum") int commentnum){
		System.out.println("Delete comment"+commentnum);
		return service.remove(commentnum) ?
				new ResponseEntity<String>(HttpStatus.OK) :
				new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
