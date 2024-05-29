package com.gl.givuluv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gl.givuluv.domain.dto.CBoardDTO;
import com.gl.givuluv.domain.dto.FollowDTO;
import com.gl.givuluv.domain.dto.LikeDTO;
import com.gl.givuluv.service.CBoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/campaign/*")
public class CampaignController {

	@Autowired
	private CBoardService service;
	
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

		// 세션 꺼내기
		HttpSession session = req.getSession();
		Object loginOrg_temp = session.getAttribute("loginOrg");
		String loginOrg = loginOrg_temp == null ?  null : loginOrg_temp.toString();
		Object loginManager_temp = session.getAttribute("loginManager");
		String loginManager = loginManager_temp == null ?  null : loginManager_temp.toString();
		if (loginOrg != null) {
			System.out.println("Org 접속");
			cboard.setType('O');
			cboard.setConnectid(loginOrg);
		} else if (loginManager != null) {
			System.out.println("Manager 접속");
			cboard.setType('M');
			cboard.setConnectid(loginManager);
		} else {
			return "campaign/cBoard";
		}

		if (service.regist(cboard, filenames)) {
			System.out.println("게시글 등록 완료");
			int cBoardnum = service.getCampaignLastNumByConnectid(cboard.getConnectid());
			return "redirect:/campaign/campaignView?cBoardnum=" + cBoardnum;
		}
		return "campaign/cBoard";
	}

	@GetMapping("campaignView")
	public String view(int cBoardnum, Model model, HttpServletRequest req) {
		System.out.println("campaignView : "+cBoardnum);
		// 게시글
		CBoardDTO cboard = service.getCampaign(cBoardnum);
		System.out.println("게시글 : "+cboard);
		// 게시글 org 프로필
		String campaignProfileFileLink = service.getCampaignProfileFileLink(cboard);
		System.out.println("게시글 단체 프로필 : "+campaignProfileFileLink);
		// 게시글 orgname
		String campaignWriterName = service.getCampaignWriterName(cboard);
		System.out.println("게시글 글쓴이 : "+campaignWriterName);
		// 게시글 좋아요 수
		int campaign_likecount = service.getlikeCount(cBoardnum);
		System.out.println("게시글 좋아요 수 : "+campaign_likecount);
		// 카테고리
		String category = service.getCategory(cboard);
		System.out.println("카테고리 : "+category);
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		String loginUser = loginUser_temp == null ?  null : loginUser_temp.toString();
		if(loginUser != null) {
			System.out.println("로그인 유저 : "+loginUser);
			// '나'와 단체의 팔로우
			FollowDTO follow = service.getfollowOfCampaign(cboard, loginUser);
			System.out.println("팔로우 : "+follow);
			// '나'의 게시글 좋아요
			LikeDTO cBoard_like = service.getCampaignLike(cBoardnum, loginUser);
			System.out.println("게시글 좋아요 : "+cBoard_like);
			
			model.addAttribute("cBoard_like", cBoard_like); 			// '나'의 게시글 좋아요 객체 // 없으면 null

			model.addAttribute("follow", follow);		// '나'와 단체의 팔로우 객체
		}
		model.addAttribute("cboard", cboard); // 게시글
		model.addAttribute("campaignProfileFileLink", campaignProfileFileLink); // 게시글 단체 프로필 파일
		model.addAttribute("campaignWriterName", campaignWriterName); // 게시글 글쓴이
		model.addAttribute("campaign_likecount", campaign_likecount); // 게시판 좋아요 수
		model.addAttribute("category", category); // 카테고리
		return "campaign/campaignView";
	}

}