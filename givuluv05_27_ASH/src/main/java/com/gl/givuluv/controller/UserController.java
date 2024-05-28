package com.gl.givuluv.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.service.DBoardService;
import com.gl.givuluv.service.MailSendService;
import com.gl.givuluv.service.OrgService;
import com.gl.givuluv.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/*")
public class UserController {
    
    @Autowired
    private UserService service;
    @Autowired
    private final MailSendService mailService;
    @Autowired
    private OrgService oservice;
    @Autowired
    private DBoardService dbservice;
    
    
    @Autowired
    public UserController(MailSendService mailService) {
        this.mailService = mailService;
    }
    
    @GetMapping("login")
    public String login() {
        return "user/login"; 
    }
    @GetMapping("logout")
    public String logout(HttpServletRequest req) {
    	System.out.println("로그아웃됨");
        req.getSession().invalidate();
        return "redirect:/";
    }
    @PostMapping("login")
    public String login(String userid, String userpw, HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if(service.login(userid, userpw)) {
            System.out.println(userid + " 로그인됨");
            session.setAttribute("loginUser", userid);
            return "redirect:/";
        } else {
            System.out.println("로그인 실패");
            return "user/login";
        }
    }
    
    @GetMapping("idFind")
    public String idFind() {
        return "user/idFind";
    }
    @GetMapping("pwFind")
    public String pwFind() {
        return "user/pwFind";
    }
    
    @GetMapping("join")
    public void replace() {}

    @PostMapping("join")
    public String join(UserDTO user, HttpServletResponse resp) {
        //회원가입 처리
        if(service.join(user)) {
            Cookie cookie = new Cookie("joinid", user.getUserid());
            cookie.setPath("/");
            cookie.setMaxAge(60);
            resp.addCookie(cookie);
        }
        else {
            //
        }
        return "redirect:login";
    }
    
    @GetMapping("checkId")
    @ResponseBody
    public String checkId(String userid) {
        if(service.checkId(userid)) {
        	System.out.println("O");
            return "O";
        }
        else {
        	System.out.println("X");
            return "X";
        }
    }   
    @GetMapping("checkNickname")
    @ResponseBody
    public String checkNickname(String nickname) {
        if(service.checkNickname(nickname)) {
            return "O";
        }
        else {
            return "X";
        }
    }   
    @PostMapping("findId")
    public String findId(String email, Model model) {
        UserDTO user = service.getUseridByEmail(email);
        model.addAttribute("userid", user.getUserid());
        return "user/findPage";                
    }
    @PostMapping("findPw")
    public String findPw(String userid, Model model) {
    	UserDTO user = service.getUserById(userid);
    	model.addAttribute("userpw", user.getUserpw());
    	return "user/findPage";                
    }
    
    //이메일 인증
  	@GetMapping("mailCheck")
  	@ResponseBody
  	public ResponseEntity<String> mailCheck(@RequestParam String nickname, @RequestParam String email) {
  		if(service.checkNickAndEmail(nickname, email)) {
  			System.out.println("이메일 인증 요청이 들어옴!");
  			System.out.println("이메일 인증 이메일 : " + email);
  			String result = mailService.joinEmail(email);
              return ResponseEntity.ok(result);			
  		} 
  		else {
  			System.out.println("일치하는 회원이 없습니다.");
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 회원이 없습니다.");		
  		}
  	}
    @GetMapping("nickAndIdCheck")
    @ResponseBody
    public String nickAndIdCheck(@RequestParam String nickname, @RequestParam String userid) {
    	if(service.nickAndIdCheck(nickname, userid)) {
    		System.out.println("O");
            return "O";
        }
        else {
        	System.out.println("X");
            return "X";
        }
    }
    //결제창에 사용자 정보 가져오기
    @GetMapping("payUserInfo")
    @ResponseBody
    public Map<String, Object> getPayUserInfo(HttpServletRequest req, HttpServletResponse resp, @RequestParam String orgname, @RequestParam int dBoardnum) {
		HttpSession session = req.getSession();
		String userid = (String)session.getAttribute("loginUser");
		UserDTO user = service.getUserById(userid);
		String orgid = oservice.getOrgidByOrgname(orgname);
		
        LocalDate today = LocalDate.now();
        String enddate = dbservice.getEnddateByBoardnum(dBoardnum);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayStr = today.format(formatter);
		System.out.println(todayStr);
		System.out.println(enddate);
		
		Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("orgid", orgid);
        response.put("today", todayStr);
        response.put("enddate", enddate);
		
        return response;	
	}
    @GetMapping("my/home")
    public String myHome(HttpServletRequest req, Model model) {
    	HttpSession session = req.getSession();
    	String loginUser = (String) session.getAttribute("loginUser");
    	
    	UserDTO userid = service.getUserById(loginUser); 
		/* List<DPaymentDTO> Donation_List = service.getDonaNameByUserid(userid); */
		String bonus = (String)service.getUserBonusById(userid);
		String d_cost = (String)service.getUserD_costById(userid);
		int donation = (int)service.getUserDonationById(userid);
		int funding = (int)service.getUserFundingById(userid);
		String UserNickName = service.getUserNickNameById(userid);
		
		List<String> Donation_List = service.getDonaNameByUserid(userid);
    	List<String> Funding_List = service.getFundNameByUserId(userid);
    	List<String> Home_Donation_List = null;
    	List<String> Home_Funding_List = null;
		
//    	아래 항목들은 userid가 아닌 Donation_List / Funding_List 에 맞는 이름과 날짜를 불러와야 함
//    	다른 부분도 마찬가지임
		List<String> D_name = service.getDonationNameById(userid);
		List<String> DU_name = service.getDonaUserNameById(userid);
		List<String> FU_name = service.getFundUserNameById(userid);
		List<String> Done_time = service.getDoneTimeById(userid);
		List<String> Fund_time = service.getFundTimeById(userid);
		
//		리스트로 받아온 객체를 최대 3개로 새로운 리스트를 만드는 로직
		if (Donation_List.size() > 3) {
            Home_Donation_List = Donation_List.subList(0, 3);
        }
        if (Funding_List.size() > 3) {
            Home_Funding_List = Funding_List.subList(0, 3);
        }

        model.addAttribute("Home_Donation_List", Home_Donation_List);
        model.addAttribute("Home_Funding_List",  Home_Funding_List);

		
		/*
		 * System.out.println(Donation_List); System.out.println(Donation_List.size());
		 * System.out.println(Funding_List); System.out.println(Funding_List.size());
		 * System.out.println(DU_name); System.out.println(FU_name);
		 */
		 
		model.addAttribute("Donation_List", Donation_List);
		model.addAttribute("Funding_List", Funding_List);
		model.addAttribute("FundUserName", FU_name);
		model.addAttribute("FundTime", Fund_time);

		model.addAttribute("NickName", UserNickName);
		model.addAttribute("UserBonus", bonus); 
		model.addAttribute("UserD_cost", d_cost);	
		model.addAttribute("UserDonate", donation);	
		model.addAttribute("UserFunding", funding);
		model.addAttribute("DonationName", D_name); 
		model.addAttribute("DoneUserName", DU_name);
		model.addAttribute("DoneTime", Done_time);
    
		/* session.getAttribute(UserNickName); */
    	if(service.checkId(loginUser)) {
			
    		
    	session.setAttribute("NickName", UserNickName);
    		
    		
    		
    		

    	}
    	return "/user/my/home";
    }
    
    @GetMapping("my/news")
    public String myNews(HttpServletRequest req, Model model) {
    	HttpSession session = req.getSession();
    	String loginUser = (String) session.getAttribute("loginUser");
    	UserDTO userid = service.getUserById(loginUser); 
		/* System.out.println(userid); */
    	List<String> Donation_List = service.getDonaNameByUserid(userid);
    	List<String> Funding_List = service.getFundNameByUserId(userid); //
    	
		String bonus = (String)service.getUserBonusById(userid);
		String d_cost = (String)service.getUserD_costById(userid);
		int donation = (int)service.getUserDonationById(userid);
		int funding = (int)service.getUserFundingById(userid);
		List<String> D_name = service.getDonationNameById(userid);
		List<String> DU_name = service.getDonaUserNameById(userid);
		List<String> FU_name = service.getFundUserNameById(userid);
		List<String> Done_time = service.getDoneTimeById(userid);
		List<String> Fund_time = service.getFundTimeById(userid);
		
		
		/*
		 * System.out.println(Donation_List); System.out.println(Donation_List.size());
		 * System.out.println(Funding_List); System.out.println(Funding_List.size());
		 * System.out.println(DU_name); System.out.println(FU_name);
		 */
		 
		model.addAttribute("Donation_List", Donation_List);
		model.addAttribute("Funding_List", Funding_List);
		model.addAttribute("FundUserName", FU_name);
		model.addAttribute("FundTime", Fund_time);
//		리스트로 받아온 객체를 add
		
		model.addAttribute("UserBonus", bonus); 
		model.addAttribute("UserD_cost", d_cost);	
		model.addAttribute("UserDonate", donation);	
		model.addAttribute("UserFunding", funding);
		model.addAttribute("DonationName", D_name); 
		model.addAttribute("DoneUserName", DU_name);
		model.addAttribute("DoneTime", Done_time);
		/* model.addAttribute("DonationNameList", Donation_List); */
		
    	return "/user/my/news";
    }
    
    @GetMapping("my/activity_history")
    public String my_activity_history() {
    	return "/user/my/activity_history";
    }
    
    @GetMapping("my/modify_userinfo")
    public String my_modify_userinfo(HttpServletRequest req, Model model) {
    	HttpSession session = req.getSession();
    	String loginUser = (String) session.getAttribute("loginUser");
    	
    	UserDTO userid = service.getUserById(loginUser); 
    	String UserNickName = service.getUserNickNameById(userid);
    	String UserName = service.getUserNameById(userid);
    	String UserEmail = service.getUserEmailById(userid);
    	String UserPhone = service.getUserPhoneById(userid);
    	
    	model.addAttribute("NickName", UserNickName);
    	model.addAttribute("UserName", UserName);
    	model.addAttribute("UserEmail", UserEmail);
    	model.addAttribute("UserPhone", UserPhone);
    	return "/user/my/modify_userinfo";
    }
    
    // 커밋테스트
    
	/*
	 * // 없어도 되는 파트임
	 * 
	 * @GetMapping("my/electronic_receipt") public String
	 * my_electronic_receipt(HttpServletRequest req, Model model) { HttpSession
	 * session = req.getSession(); String loginUser = (String)
	 * session.getAttribute("loginUser"); UserDTO userid =
	 * service.getUserById(loginUser); String bonus =
	 * (String)service.getUserBonusById(userid); String d_cost =
	 * (String)service.getUserD_costById(userid); int donation =
	 * (int)service.getUserDonationById(userid); int funding =
	 * (int)service.getUserFundingBiId(userid); List<String> D_name =
	 * service.getDonationNameById(userid); List<String> DU_name =
	 * service.getDonaUserNameById(userid); List<String> Done_time =
	 * service.getDoneTimeById(userid);
	 * 
	 * 
	 * model.addAttribute("UserBonus", bonus); model.addAttribute("UserD_cost",
	 * d_cost); model.addAttribute("UserDonate", donation);
	 * model.addAttribute("UserFunding", funding);
	 * model.addAttribute("DonationName", D_name);
	 * model.addAttribute("DoneUserName", DU_name); model.addAttribute("DoneTime",
	 * Done_time); // 영수증을 다운로드 할 수 있는 페이지 return "/user/my/electronic_receipt"; }
	 */
}
