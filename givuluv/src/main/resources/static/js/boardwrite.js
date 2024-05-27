package com.gl.givuluv.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gl.givuluv.domain.dto.CBoardDTO;
import com.gl.givuluv.domain.dto.CommentDTO;
import com.gl.givuluv.domain.dto.LikeDTO;
import com.gl.givuluv.service.CBoardService;
import com.gl.givuluv.service.CommentService;
import com.gl.givuluv.service.LikeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/campaign/*")
public class CampaignController {
	
	@Autowired
	private CBoardService cbservice;

	@Autowired
	private CommentService cmservice;
	
	@Autowired
	private LikeService lservice;
	
	@GetMapping("cBoard")
	public String cBoardList(Model model) {
		return "campaign/cBoard";
	}
	
	@GetMapping("write")
	public void write() {
		System.out.println("campaign/write");
	}
	
	@PostMapping("write")
	public String regist(CBoardDTO cboard, String filenames, HttpServletRequest req) {
		System.out.println("post : campaign/write");
		
		System.out.println(cboard.getCContent());
		System.out.println(filenames);
		
		HttpSession session = req.getSession();
	    if(!(session.getAttribute("loginOrg").toString()).equals("")){
	    	System.out.println("Org 접속");
	    	cboard.setType('O');
	    	cboard.setConnectid(session.getAttribute("loginOrg").toString());
	    }
	    else if(!(session.getAttribute("loginManager").toString()).equals("")) {
	    	System.out.println("Manager 접속");
	    	cboard.setType('M');
	    	cboard.setConnectid(session.getAttribute("loginManager").toString());
	    }
	    else {
	    	return "campaign/cBoard";
	    }
	    
	    if(cbservice.regist(cboard, filenames)) {
	    	System.out.println("게시글 등록 완료");
	    	int cBoardnum = cbservice.getCampaignLastNumByConnectid(cboard.getConnectid());
	    	return "redirect:/campaign/campaignView?cBoardnum="+cBoardnum;
	    }
	    return "campaign/cBoard";
	}
	
	@GetMapping("campaignView")
	public String view(int cBoardnum, Model model) {
		CBoardDTO cboard = cbservice.getCampaign(cBoardnum);
		List<CommentDTO> comments = cmservice.getComments(cBoardnum);
		LikeDTO campaign_like = new LikeDTO();
		campaign_like.setConnectid(cBoardnum);
		campaign_like.setType('C');
		int campaign_likecount = lservice.likeCount(campaign_like);
		//게시판 category
		if(cboard.getType() == 'O') {
			//카테고리 가져오기
		}
		
		model.addAttribute("cboard",cboard);	//게시판
		model.addAttribute("campaign_like",campaign_likecount);	//게시판 좋아요 수

		
		
		//ajax?
		//'내'가 게시판 좋아요 했는지
		model.addAttribute("comments",comments);	//댓글들
		// 댓글 좋아요 
		//'내'가 댓글 좋아요 했는지
		
		return "campaign/campaignView";
	}
	
	
	
}
