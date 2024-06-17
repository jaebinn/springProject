package com.gl.givuluv.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.service.MailSendService;
import com.gl.givuluv.service.OrgService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/org/*")
public class OrgController {
	
	@Autowired
	private OrgService service;
	
	@GetMapping("join")
	public String orgjoin() {
		return "org/join";
	}
	
	@PostMapping("join")
	public String join(OrgDTO org, MultipartFile[] files, HttpServletResponse resp) throws Exception {
		//회원가입 처리
		
		System.out.println("OrgController에서 찍힌 파일 : "+files);
		
		if(service.join(org, files)) {
			System.out.println("org회원가입성공!");
			Cookie cookie = new Cookie("joinid", org.getOrgid());
			cookie.setPath("/");
			cookie.setMaxAge(60);
			resp.addCookie(cookie);
		}
		else {
			//
		}
		return "user/login";
	}
	 @GetMapping("login")
	    public String loginPage(HttpSession session) {
	        if (session.getAttribute("loginOrg") != null) {
	            // 이미 로그인된 경우 홈 페이지로 리다이렉트
	            return "redirect:/";
	        }
	        // 로그인 페이지로 이동
	        return "user/login";
	    }
	@PostMapping("login")
	   public String login(String orgid, String orgpw, HttpServletRequest req, HttpServletResponse resp, String type, Model model) {
	       HttpSession session = req.getSession();
	       if(service.login(orgid, orgpw)) {
	           System.out.println(orgid + " 로그인됨");
	           session.setAttribute("loginOrg", orgid);
	           return "redirect:/";
	       } else {
	           System.out.println("로그인 실패");
				/*
				 * Cookie cookie = new Cookie("orgLoginErr", "로그인실패"); cookie.setPath("/");
				 * cookie.setMaxAge(60); resp.addCookie(cookie);
				 */
	           model.addAttribute("orgLoginErr", "로그인실패");
				
				 
	           model.addAttribute("type", type);
	           return "/user/login";
	       }
	   }

	
	
	@GetMapping("checkId")
	@ResponseBody
	public String checkId(String orgid) {
		if(service.checkId(orgid)) {
			return "O";
		}
		else {
			return "X";
		}
	}
	@GetMapping("checkPw")
	@ResponseBody
	public String checkPw(String orgpw) {
		if(service.checkPw(orgpw)) {
			return "O";
		}
		else {
			return "X";
		}
	}
	@GetMapping("register")
	public String register(HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession();
		String loginOrg = (String)session.getAttribute("loginOrg");
		
		char check = service.checkRegisterByOrgid(loginOrg);
		
		String url;
		
		if(check == 'o') {
			url = "/";
			model.addAttribute("alertMessage", "이미 승인된 단체입니다.");
			model.addAttribute("redirectUri", url);
			return "store/storeMassege";
		}
		else if(check == '-'){
			url = "/";
			model.addAttribute("alertMessage", "사회단체 승인 대기 중입니다.");
			model.addAttribute("redirectUri", url);
			return "store/storeMassege";
		}
		else {
			return "org/register";
		}
	}
	@GetMapping("logsignup")
	public String logsignup() {
		return "org/logsignup";
	}
	@GetMapping("groupinfo")
	 public String groupinfo() {
	    return "org/groupinfo";
	}
	
	 @GetMapping("signupcomplete")
	 public String signupcomplete() {
	    return "org/signupcomplete";
	}
	 @GetMapping("checkunqnum")
		@ResponseBody
		public String checkunqnum(@RequestParam(value = "orgunqnum", required = false) Integer orgunqnum) {
		    if (orgunqnum == null) {
		        return "X";
		    }
		    System.out.println(service.checkUnqnumber(orgunqnum));
		    if (service.checkUnqnumber(orgunqnum)) {
		        return "O";
		    } else {  
		        return "X";
		    }
		}
	 @GetMapping("checkunqnumber")
		@ResponseBody
		public String checkunqnum(int orgunqnum) {
		 System.out.println(service.checkUnqnumber(orgunqnum));
		 	if(service.checkUnqnumber(orgunqnum)) {
	            return "X";
	        }
	        else {
	            return "O";
	        }
		}
	//인덱스 org프로필
	@GetMapping("getOrgProfile")
	@ResponseBody
	public List<Map<String, String>> getOrgProfile(){
		List<Map<String, String>> orgprofile = service.getOrgProfile();
		return orgprofile;
	}
	
	@GetMapping("my/home")
	public String home(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String src = "/summernoteImage/";
//		세션에 세팅된 [orgid]를 불러옵니다.
		String orgid = (String) session.getAttribute("loginOrg");
		System.out.println("orgid :: "+orgid);
		
//		orgid로 org정보 전체를 불러옵니다.
		OrgDTO orginfo = service.getOrgInfo(orgid);
		System.out.println("orginfo :: "+orginfo);
		//String d_boardnum = service.getD_boardnum(orgid);
	   // String f_boardnum = service.getF_boardnum(orgid);
		String orgSystenmane = service.getOrgSystemname(orgid);
		System.out.println("orgSystenmane :: "+orgSystenmane);
		
		String like_cnt = service.getLike_cnt(orgid);
		String review_cnt = service.getReview_cnt(orgid);
		String follow_cnt = service.getFollow_cnt(orgid);
		
//		모델 
		model.addAttribute("orgid", orgid);
		model.addAttribute("orginfo", orginfo);
		model.addAttribute("orgsystemname", src+orgSystenmane);
		model.addAttribute("like_cnt", like_cnt);
		model.addAttribute("review_cnt", review_cnt);
		model.addAttribute("follow_cnt", follow_cnt);
		
		System.out.println("model정상작동 home으로 넘어갑니다.");
		return "/org/my/home";
	}
	@GetMapping("my/modify_orginfo")
	public String modify_orginfo(HttpServletRequest req, Model model) {
		String src = "/summernoteImage/";
		HttpSession session = req.getSession();

//		세션에 세팅된 [orgid]를 불러옵니다.
		String orgid = (String) session.getAttribute("loginOrg");
		System.out.println("orgid :: "+orgid);
		
//		orgid로 org정보 전체를 불러옵니다.
		OrgDTO orginfo = service.getOrgInfo(orgid);
		System.out.println("orginfo :: "+orginfo);
		
//		모델 
		String orgSystemname = service.getOrgSystemname(orgid);
		System.out.println("orgSystenmane :: "+orgSystemname);

		model.addAttribute("orgsystemname", src+orgSystemname);
		model.addAttribute("orgid", orgid);
		model.addAttribute("orginfo", orginfo);
		return "org/my/modify_orginfo";
	}
	
	@GetMapping("modify")
	public String modify() {
		return "org/my/modify_orginfo";
	}

	@PostMapping("my/modify_orginfo")
	public String modify(OrgDTO org, HttpServletRequest req, Model model, MultipartFile files) throws Exception {
		String src = "/summernoteImage/";
		HttpSession session = req.getSession();
		String orgid = (String) session.getAttribute("loginOrg");
		org.setOrgid(orgid);
		System.out.println("입력 받은org :: "+org);
		System.out.println("입받은 file :: "+files);
		System.out.println("post::modify");
		String orgSystename = service.getOrgSystemname(orgid);
		System.out.println("orgSystenmane :: "+src+orgSystename);
		
		  if(service.modify(org, files)) {
			  System.out.println("OrgModify Ok"); } 
		  else {
				System.out.println("OrgModify flase"); }
		 
		System.out.println("fileName :: "+files);
		model.addAttribute("orgsystemname", src+orgSystename);
		return "redirect:home";
	}
	
	@GetMapping("my/org_activity_history")
	   public String org_activity_history(HttpServletRequest req, Model model) {
	      HttpSession session = req.getSession();
	      
	      String src = "/summernoteImage/";
//	      세션에 세팅된 [orgid]를 불러옵니다.
	      String orgid = (String) session.getAttribute("loginOrg");
	      String orgSystenmane = service.getOrgSystemname(orgid);
	      
	      List <String> d_boardList = service.getD_board(orgid);
	      List <String> f_boardList = service.getF_board(orgid);
	      OrgDTO orginfo = service.getOrgInfo(orgid);
	      
	      List<Map<String, Object>> d_boardinfo = service.getD_boardinfo(orgid);
	      
			
	      
	      List<Map<String, Object>> f_boardinfo = service.getF_boardinfo(orgid);
	      
			
	      List<Map<String, Object>> followinfo = service.getFollowinfo(orgid);
	      
	      
	      String d_boardnum = service.getD_boardnum(orgid);
	      String f_boardnum = service.getF_boardnum(orgid);
	      
	      List<Map<String, Object>> likeinfo = service.getLikeinfo(d_boardnum);
	      
	      
	      
	      List<Map<String, Object>> reviewinfo = service.getReviewinfo(f_boardnum);
	      
	      
	      System.out.println("d_boardList :: "+d_boardList);
	      System.out.println("f_boardList :: "+f_boardList);
	      
	      model.addAttribute("orgsystemname", src+orgSystenmane);
	      model.addAttribute("orgid", orgid);
	      model.addAttribute("d_boardList", d_boardList);
	      model.addAttribute("f_boardList", f_boardList);
	      model.addAttribute("orginfo", orginfo);
			/*
			 * model.addAttribute("d_boardtitle", d_boardtitle);
			 * model.addAttribute("target_amount", target_amount);
			 * model.addAttribute("save_money", save_money);
			 * model.addAttribute("f_boardtitle", f_boardtitle);
			 * model.addAttribute("f_target_amount", f_target_amount);
			 * model.addAttribute("f_save_money", f_save_money);
			 */
	     
	     
	    
	      
	      model.addAttribute("d_boardinfo", d_boardinfo);
	      model.addAttribute("f_boardinfo", f_boardinfo);
	      model.addAttribute("reviewinfo", reviewinfo);
	      model.addAttribute("followinfo", followinfo);
	      model.addAttribute("likeinfo", likeinfo);
	      
	      System.out.println("followinfo :: "+followinfo);
	      System.out.println("likeinfo :: "+likeinfo);
	      System.out.println("f_boardinfo :: "+f_boardinfo);
	      System.out.println("d_boardinfo :: "+d_boardinfo);
	      //\model.addAttribute("review_star", review_star);
	      System.out.println("컨트롤러 도착함 히스토리로 넘어감");
	      return "org/my/org_activity_history";
	   }
	
	@GetMapping("my/org_news")
	public String org_news(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();

//		세션에 세팅된 [orgid]를 불러옵니다.
		String orgid = (String) session.getAttribute("loginOrg");
		
		OrgDTO orginfo = service.getOrgInfo(orgid);
		model.addAttribute("orginfo", orginfo);
		System.out.println("컨트롤러 도착. 뉴스로 넘어감");
		return"org/my/org_news";
	}
	@GetMapping("my/delete_orginfo")
	   public String deleteUser(HttpServletRequest req, Model model) {
	      HttpSession session = req.getSession();
	      String src = "/summernoteImage/";
	      session.getAttribute("loginOrg");
	      String orgid = (String) session.getAttribute("loginOrg");
	      OrgDTO orginfo = service.getOrgInfo(orgid);
	      String orgSystenmane = service.getOrgSystemname(orgid);
	      
	      model.addAttribute("loginOrg", orgid);
	      model.addAttribute("orginfo", orginfo);
	      model.addAttribute("orgsystemname", src+orgSystenmane);
	      System.out.println("컨트롤러로 요청 들어옴 페이지로 이동함");
	      return"org/my/delete_orginfo";
	   }
	
	@PostMapping("delete")
	public String delete(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		session.getAttribute("loginOrg");
		System.out.println("delete controller로 넘어옴");
		String orgid = (String) session.getAttribute("loginOrg");
		System.out.println("orgid :: "+orgid);
		String d_boardnum = service.getD_boardnum(orgid);
		System.out.println("d_boardnum:: "+d_boardnum);
		String f_boardnum = service.getF_boardnum(orgid);
		/*
		 * if(service.deleteF_detail(loginUser)) { System.out.println("F_detail 삭제완료");
		 * } else { System.out.println("F_detail 정보가 없는 회원입니다"); }
		 * if(service.deleteD_detail(loginUser)) { System.out.println("D_detail 삭제완료");
		 * } else { System.out.println("D_detail 정보가 없는 회원입니다"); }
		 */
		if(service.deleteFollow(orgid)) {
			System.out.println("Follow 삭제완료");
		}
		else {
			System.out.println("Follow 정보가 없는 회원입니다");
		}
		if(service.deleteD_Like(d_boardnum)) {
			System.out.println("Like 삭제완료");
		}
		else {
			System.out.println("Like 정보가 없는 회원입니다");
		}
		if(service.deleteF_Like(f_boardnum)) {
			System.out.println("Like 삭제완료");
		}
		else {
			System.out.println("Like 정보가 없는 회원입니다");
		}
		if(service.deleteD_Review(d_boardnum)) {
			System.out.println("Review 삭제완료");
		}
		else {
			System.out.println("Review 정보가 없는 회원입니다");
		}
		if(service.deleteF_Review(f_boardnum)) {
			System.out.println("Review 삭제완료");
		}
		else {
			System.out.println("Review 정보가 없는 회원입니다");
		}
		if(service.deleteD_payment(orgid)) {
			System.out.println("D_payment 삭제완료");
		}
		else {
			System.out.println("D_payment 정보가 없는 회원입니다");
		}
		if(service.deleteF_payment(orgid)) {
			System.out.println("F_payment 삭제완료");
		}
		else {
			System.out.println("F_payment 정보가 없는 회원입니다");
		}
		if(service.deleteO_approve(orgid)) {
			System.out.println("O_approve 삭제완료");
		}
		else {
			System.out.println("O_approve 정보가 없는 회원입니다");
		}
		if(service.deleteO_register(orgid)) {
			System.out.println("O_register 삭제완료");
		}
		else {
			System.out.println("O_register 정보가 없는 회원입니다");
		}
		if(service.deleteD_board(orgid)) {
			System.out.println("deleteD_board 삭제완료");
		}
		else {
			System.out.println("deleteD_board 정보가 없는 회원입니다");
		}
		if(service.deleteF_board(orgid)) {
			System.out.println("deleteF_board 삭제완료");
		}
		else {
			System.out.println("deleteF_board 정보가 없는 회원입니다");
		}
		
		if(service.deleteOrg(orgid)) {
			System.out.println("모든정보 삭제완료");
			req.getSession().invalidate();
			
		}
		return "redirect:/";
	}
	
	 @GetMapping("checkuserPw")
	    @ResponseBody
	    public String checkuwerPw(String orgpw,HttpServletRequest req, Model model) {
		 HttpSession session = req.getSession();
			session.getAttribute("loginOrg");
			System.out.println("delete controller로 넘어옴");
			String orgid = (String) session.getAttribute("loginOrg");
		 OrgDTO orginfo = service.getOrgInfo(orgid);
		 
	        if(orgpw.equals(orginfo.getOrgpw())) {
	        	System.out.println("O");
	            return "O";
	        }
	        else {
	        	System.out.println("X");
	            return "X";
	        }
	    }   

}
