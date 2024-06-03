package com.gl.givuluv.controller;

import java.util.List;
import java.util.Map;

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
import com.gl.givuluv.domain.dto.LikeDTO;
import com.gl.givuluv.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/comment/*")
public class CommentController {
	@Autowired
	private CommentService service;

	@GetMapping("commentList")
	public ResponseEntity<List<CommentPageDTO>> commentList(int cBoardnum, int commentlastnum, HttpServletRequest req) {
		System.out.println("Get : /comment/commentList");
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		String loginUser = loginUser_temp == null ? null : loginUser_temp.toString();
		return new ResponseEntity<List<CommentPageDTO>>(service.getCommentList(cBoardnum, commentlastnum, loginUser),
				HttpStatus.OK);
	}

	@GetMapping("cBoard")
	public ResponseEntity<Map<String, Object>> getCBoard(int cboardlastnum, HttpServletRequest req) {
		System.out.println("Get : /comment/cBoard");
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		if (loginUser_temp != null) {
			// User // 카테고리로 board 거르기 + manager 글

		} else {
			// User 제외 나머지 // 모든 게시글 보이기

		}

		return new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
	}

	@PostMapping(value = "regist", consumes = "application/json", produces = "application/json;charset=utf-8")
	public ResponseEntity<CommentDTO> regist(@RequestBody CommentDTO comment, HttpServletRequest req) {
		System.out.println("Post : /comment/commentRegist");
		if (service.commentSpaceCheck(comment.getCommentdetail())) {
			return new ResponseEntity<CommentDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 세션 검사
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		Object loginOrg_temp = session.getAttribute("loginOrg");
		if (loginUser_temp != null) {
			System.out.println("로그인 user : " + loginUser_temp);
			comment.setType('U');
			comment.setConnectid(loginUser_temp.toString());
		} else if (loginOrg_temp != null) {
			System.out.println("로그인 org : " + loginUser_temp);
			comment.setType('O');
			comment.setConnectid(loginOrg_temp.toString());
		} else {
			System.out.println("실패");
			System.out.println("로그인 user : " + loginUser_temp);
			System.out.println("로그인 org : " + loginUser_temp);
			return new ResponseEntity<CommentDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println("session 검사 완료");

		CommentDTO result = service.registComment(comment);
		System.out.println("댓글 등록 완료 : " + result);
		return result != null ? new ResponseEntity<CommentDTO>(result, HttpStatus.OK)
				: new ResponseEntity<CommentDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PatchMapping(value = "{commentnum}", consumes = "application/json")
	public ResponseEntity<CommentDTO> modify(@RequestBody CommentDTO comment, HttpServletRequest req) {
		System.out.println("Patch : /comment/modify");
		if (service.commentSpaceCheck(comment.getCommentdetail())) {
			return new ResponseEntity<CommentDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		Object loginOrg_temp = session.getAttribute("loginOrg");

		// 세션 검사
		if (loginUser_temp != null) {
			System.out.println("로그인 user : " + loginUser_temp);
			comment.setType('U');
			comment.setConnectid(loginUser_temp.toString());
		} else if (loginOrg_temp != null) {
			System.out.println("로그인 org : " + loginUser_temp);
			comment.setType('O');
			comment.setConnectid(loginOrg_temp.toString());
		} else {
			System.out.println("실패");
			System.out.println("로그인 user : " + loginUser_temp);
			System.out.println("로그인 org : " + loginUser_temp);
			return new ResponseEntity<CommentDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		CommentDTO result = service.modify(comment);
		return result != null ? new ResponseEntity<CommentDTO>(result, HttpStatus.OK)
				: new ResponseEntity<CommentDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping(value = "{commentnum}")
	public ResponseEntity<String> remove(@PathVariable("commentnum") int commentnum) {
		System.out.println("Delete : /comment/" + commentnum);
		return service.remove(commentnum) ? new ResponseEntity<String>(HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("likeregist")
	public ResponseEntity<String> likeregist(@RequestBody LikeDTO like, HttpServletRequest req) {
		System.out.println("/comment/likeregist");
		System.out.println(like);

		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		String loginUser = loginUser_temp == null ? null : loginUser_temp.toString();
		if (loginUser != null) {
			like.setUserid(loginUser);

			CommentDTO comment = new CommentDTO();
			comment.setCommentnum(like.getConnectid());
			if (service.getCommentLike(comment, loginUser) == null) {
				if (service.insertLike(like)) {
					String result = service.getCommentLikeCount(comment)+"";
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping("likecancel")
	public ResponseEntity<String> likecancel(@RequestBody LikeDTO like, HttpServletRequest req) {
		System.out.println("/comment/likecancel");
		System.out.println(like);
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		String loginUser = loginUser_temp == null ? null : loginUser_temp.toString();
		if (loginUser != null) {
			like.setUserid(loginUser);
			like.setType('R');
			if (service.cancelLike(like)) {
				CommentDTO comment = new CommentDTO();
				comment.setCommentnum(like.getConnectid());
				String result = service.getCommentLikeCount(comment)+"";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
