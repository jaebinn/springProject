package com.gl.givuluv.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gl.givuluv.domain.dto.CBoardDTO;
import com.gl.givuluv.domain.dto.FollowDTO;
import com.gl.givuluv.domain.dto.LikeDTO;
import com.gl.givuluv.service.CBoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/campaign/*")
public class CampaignController {

	@Autowired
	private CBoardService service;
	
	@GetMapping("cBoard")
	public String cBoard(Model model) {
		return "campaign/cBoard";
	}
	@PostMapping("boardList")
	public ResponseEntity<Map<String, Object>> cBoardList(int boardlastnum, HttpServletRequest req) {
		Map<String, Object> result = new HashMap<>();
		System.out.println("campaign/boardList");
		System.out.println(boardlastnum);
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		List<CBoardDTO> campaignList = new ArrayList<CBoardDTO>();
		
		if(loginUser_temp != null) {
			// 유저
			String loginUser = loginUser_temp.toString();
			System.out.println(loginUser);
			
			campaignList = service.getCampaignBoardListOfUser(loginUser, boardlastnum, 4);
			
			List<FollowDTO> followList = new ArrayList<FollowDTO>();
			List<LikeDTO> likeList = new ArrayList<LikeDTO>();
			for (CBoardDTO campaign : campaignList) {
				System.out.println(campaign);
				// 나와 단체의 팔로우
				FollowDTO follow = service.getfollowOfCampaign(campaign, loginUser);
				// '나'의 게시글 좋아요
				LikeDTO cBoard_like = service.getCampaignLike(campaign.getCBoardnum(), loginUser);
				
				followList.add(follow);
				likeList.add(cBoard_like);
			}
			result.put("followList", followList);
			result.put("likeList", likeList);
		}
		else {
			// 유저 외 - 모든 게시글 보이기
			campaignList = service.getCampaignList(boardlastnum, 4);
		}
		
		List<String> campaignProfileFileLinkList = new ArrayList<String>(); // org 프로필
		List<String> campaignWriterNameList = new ArrayList<String>(); // orgname
		List<Integer> campaign_likecountList = new ArrayList<Integer>(); // 좋아요 수
		List<String> categoryList = new ArrayList<String>(); // 카테고리
		
		for (CBoardDTO campaign : campaignList) {
			String campaignProfileFileLink = service.getCampaignProfileFileLink(campaign);
			String campaignWriterName = service.getCampaignWriterName(campaign);
			int campaign_likecount = service.getlikeCount(campaign.getCBoardnum());
			String category = service.getCategory(campaign);
			
			campaignProfileFileLinkList.add(campaignProfileFileLink);
			campaignWriterNameList.add(campaignWriterName);
			campaign_likecountList.add(campaign_likecount);
			categoryList.add(category);
		}
		
		result.put("campaignList", campaignList);
		result.put("campaignProfileFileLinkList", campaignProfileFileLinkList);
		result.put("campaignWriterNameList", campaignWriterNameList);
		result.put("campaign_likecountList", campaign_likecountList);
		result.put("categoryList", categoryList);

		System.out.println(result);
		
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
	

	@GetMapping("write")
	   public String write(HttpServletRequest req, Model model) {
	      String url = "/";
	      HttpSession session = req.getSession();
	      Object loginOrg_temp = session.getAttribute("loginOrg");
	      Object loginManager_temp = session.getAttribute("loginManager");
	      if(loginOrg_temp != null) {
	         String loginOrg = loginOrg_temp.toString();
	         if(service.isApproveOrg(loginOrg) == 1) {
	            return "campaign/write";
	         }
	         else if(service.isApproveOrgX(loginOrg) == 1){
	            System.out.println("승인이 필요한 사회단체입니다.");
	            model.addAttribute("alertMessage", "단체 승인 대기 중입니다.\n승인 후 게시판 등록이 가능합니다.");
	            model.addAttribute("redirectUri", url);
	            return "store/storeMassege";
	         }else {
	            url ="/org/register";
	            model.addAttribute("alertMessage", "단체 가입 후 게시판 등록이 가능합니다.");
	            model.addAttribute("redirectUri", url);
	            return "store/storeMassege";
	         }
	      }
	      if(loginManager_temp != null) {
	         return "campaign/write";
	      }
	      return "/";
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
			cboard.setType('A');
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
	
	@PostMapping("likeregist")
	public ResponseEntity<String> likeregist(@RequestBody LikeDTO like, HttpServletRequest req) {
		System.out.println("/campaign/likeregist");
		System.out.println(like);
		
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		String loginUser = loginUser_temp == null ?  null : loginUser_temp.toString();
		if(loginUser != null) {
			like.setUserid(loginUser);
			if(service.getCampaignLike(like.getConnectid(), loginUser) == null) {
				if(service.insertLike(like)) {
					String result = service.getlikeCount(like.getConnectid())+"";
					return new ResponseEntity<String>(result, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@DeleteMapping("likecanacel")
	public ResponseEntity<String> likecancel(@RequestBody LikeDTO like, HttpServletRequest req) {
		System.out.println("/campaign/likecancel");
		System.out.println(like);
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		String loginUser = loginUser_temp == null ?  null : loginUser_temp.toString();
		if(loginUser != null) {
			like.setUserid(loginUser);
			if(service.cancelLike(like)) {
				String result = service.getlikeCount(like.getConnectid())+"";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}