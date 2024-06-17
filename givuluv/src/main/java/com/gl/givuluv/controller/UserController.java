package com.gl.givuluv.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.CBoardDTO;
import com.gl.givuluv.domain.dto.Criteria;
import com.gl.givuluv.domain.dto.FollowDTO;
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
	public String loginPage(HttpSession session) {
		if (session.getAttribute("loginUser") != null || session.getAttribute("loginSeller") != null
				|| session.getAttribute("loginOrg") != null || session.getAttribute("loginManager") != null) {
			// 이미 로그인된 경우 홈 페이지로 리다이렉트
			return "redirect:/";
		}
		// 로그인 페이지로 이동
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
		if (service.login(userid, userpw)) {
			System.out.println(userid + " 로그인됨");
			session.setAttribute("loginUser", userid);
			return "redirect:/";
		} else {
			System.out.println("로그인 실패");
			model.addAttribute("loginFailed", "로그인실패");
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
	public void replace() {
	}

	@PostMapping("join")
	public String join(UserDTO user, HttpServletResponse resp) {
		// 회원가입 처리
		if (service.join(user)) {
			System.out.println(user.getUserid());
			Cookie cookie = new Cookie("joinid", user.getUserid());
			cookie.setPath("/");
			cookie.setMaxAge(60);
			resp.addCookie(cookie);
		} else {
			//
		}
		return "redirect:login";
	}

	@GetMapping("checkId")
	@ResponseBody
	public String checkId(String userid) {
		if (service.checkId(userid)) {
			System.out.println("O");
			return "O";
		} else {
			System.out.println("X");
			return "X";
		}
	}

	@GetMapping("checkNickname")
	@ResponseBody
	public String checkNickname(String nickname) {
		if (service.checkNickname(nickname)) {
			return "O";
		} else {
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

	// 이메일 인증
	@GetMapping("mailCheck")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> mailCheck(@RequestParam String nickname, @RequestParam String email) {
		Map<String, Object> response = new HashMap<>();
		if (service.checkNickAndEmail(nickname, email)) {
			System.out.println("이메일 인증 요청이 들어옴!");
			System.out.println("이메일 인증 이메일 : " + email);
			String authCode = mailService.joinEmail(email);

			int validityPeriod = 3 * 60;
			response.put("authCode", authCode);
			response.put("validityPeriod", validityPeriod);
			return ResponseEntity.ok(response);
		} else {
			System.out.println("일치하는 회원이 없습니다.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap("error", "일치하는 회원이 없습니다."));
		}
	}

	@GetMapping("nickAndIdCheck")
	@ResponseBody
	public String nickAndIdCheck(@RequestParam String nickname, @RequestParam String userid) {
		if (service.nickAndIdCheck(nickname, userid)) {
			System.out.println("O");
			return "O";
		} else {
			System.out.println("X");
			return "X";
		}
	}

	// 결제창에 사용자 정보 가져오기
	@GetMapping("payUserInfo")
	@ResponseBody
	public Map<String, Object> getPayUserInfo(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam String orgname, @RequestParam int dBoardnum) {
		HttpSession session = req.getSession();
		String userid = (String) session.getAttribute("loginUser");
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
		String bonus = service.getUserBonusById(userid);
		String d_cost = service.getUserD_costById(userid);
		int donation = service.getUserDonationById(userid);
		int funding = service.getUserFundingBiId(userid);
		String D_name = service.getDonationNameById(userid);
		String DU_name = service.getDonaUserNameById(userid);
		String Done_time = service.getDoneTimeById(userid);
		String NickName = service.getNicknameById(userid);

		model.addAttribute("UserBonus", bonus);
		model.addAttribute("UserD_cost", d_cost);
		model.addAttribute("UserDonate", donation);
		model.addAttribute("UserFunding", funding);
		model.addAttribute("DonationName", D_name);
		model.addAttribute("DoneUserName", DU_name);
		model.addAttribute("DoneTime", Done_time);
		model.addAttribute("NickName", NickName);
		/* model.addAttribute("DonationNameList", Donation_List); */

		if (service.checkId(loginUser)) {

//    		하나도 적용이 안되고 있음

		}
		return "/user/my/home";
	}

	@GetMapping("my/news")
	public String myNews(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String loginUser = (String) session.getAttribute("loginUser");
		UserDTO userid = service.getUserById(loginUser);
		/* System.out.println(userid); */
		List<Map<String, Object>> Donation_List = service.getDonaNameByUserid(userid);
		List<Map<String, Object>> RDonation_List = service.getRDonaNameByUserid(userid);
		List<Map<String, Object>> Funding_List = service.getFundNameByUserid(userid);

		String bonus = (String) service.getUserBonusById(userid);
		String d_cost = (String) service.getUserD_costById(userid);
		int donation = (int) service.getUserDonationById(userid);
		int funding = (int) service.getUserFundingBiId(userid);

		System.out.println("기부리스트: " + Donation_List);
		System.out.println("정기 기부리스트: " + RDonation_List);
		System.out.println("펀딩리스트: " + Funding_List);
		System.out.println(Funding_List.size());

		model.addAttribute("RDonation_List", RDonation_List);
		model.addAttribute("Donation_List", Donation_List);
		model.addAttribute("Funding_List", Funding_List);
		model.addAttribute("UserDonate", donation);
		model.addAttribute("UserBonus", bonus);
		model.addAttribute("UserD_cost", d_cost);
		model.addAttribute("UserFunding", funding);
		/* model.addAttribute("DonationNameList", Donation_List); */

		return "/user/my/news";
	}

	/*
	 * @GetMapping("my/activity_history") public String
	 * my_activity_history(HttpServletRequest req, Model model) { String src =
	 * "/summernoteImage/"; HttpSession session = req.getSession(); String loginUser
	 * = (String) session.getAttribute("loginUser"); UserDTO userid =
	 * service.getUserById(loginUser); List<Map<String, Object>> Donation_List =
	 * service.getDonaNameByUserid(userid); List<Map<String, Object>> Funding_List =
	 * service.getFundNameByUserid(userid); List<String> d_systemname =
	 * service.getSystemNameByUserid(userid); List<String> f_systemname =
	 * service.getF_SystemName(userid); model.addAttribute("Donation_List",
	 * Donation_List); model.addAttribute("Funding_List", Funding_List);
	 * model.addAttribute("d_systemname", src+d_systemname);
	 * model.addAttribute("f_systemname", src+f_systemname);
	 * 
	 * System.out.println("Donation_List :: "+Donation_List);
	 * System.out.println("Funding_List :: "+Funding_List);
	 * 
	 * return "/user/my/activity_history"; }
	 */

	@GetMapping("my/electronic_receipt")
	public String my_electronic_receipt(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String loginUser = (String) session.getAttribute("loginUser");
		UserDTO userid = service.getUserById(loginUser);
		String bonus = (String) service.getUserBonusById(userid);
		String d_cost = (String) service.getUserD_costById(userid);
		int donation = (int) service.getUserDonationById(userid);
		int funding = (int) service.getUserFundingBiId(userid);
		String D_name = (String) service.getDonationNameById(userid);
		String DU_name = (String) service.getDonaUserNameById(userid);
		String Done_time = (String) service.getDoneTimeById(userid);

		model.addAttribute("UserBonus", bonus);
		model.addAttribute("UserD_cost", d_cost);
		model.addAttribute("UserDonate", donation);
		model.addAttribute("UserFunding", funding);
		model.addAttribute("DonationName", D_name);
		model.addAttribute("DoneUserName", DU_name);
		model.addAttribute("DoneTime", Done_time);
		// 영수증을 다운로드 할 수 있는 페이지
		return "/user/my/electronic_receipt";
	}

//  유저 정보를 수정하는 수정페이지 
//  유저 정보를 수정하는 수정페이지 
	@GetMapping("my/modify_userinfo")
	public String my_modify_userinfo(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String loginUser = (String) session.getAttribute("loginUser");

		UserDTO userid = service.getUserById(loginUser);
		UserDTO userinfo = service.getUserInfo(userid);
		String UserNickName = service.getUserNickNameById(userid);
		String UserName = service.getUserNameById(userid);
		String UserEmail = service.getUserEmailById(userid);
		String UserPhone = service.getUserPhoneById(userid);

		model.addAttribute("NickName", UserNickName);
		model.addAttribute("UserName", UserName);
		model.addAttribute("UserEmail", UserEmail);
		model.addAttribute("UserPhone", UserPhone);
		model.addAttribute("userinfo", userinfo);
		return "/user/my/modify_userinfo";
	}

	@PostMapping("modify")
	public String modify(UserDTO user, HttpServletRequest req) {
		// 회원정보 업데이트(수정)
		HttpSession session = req.getSession();
		String loginUser = (String) session.getAttribute("loginUser");

		System.out.println("세션의 userid값 :: " + loginUser);
		System.out.println("입력된 이름 :: " + user.getUsername());
		System.out.println("입력된 이메일 :: " + user.getEmail());
		System.out.println("입력된 닉네임 :: " + user.getNickname());
		System.out.println("입력된 전화번호 ::" + user.getUserphone());

		System.out.println();

		if (service.UpdateUserInfo(user, loginUser)) {
			System.out.println("업데이트 성공");
		} else {
			System.out.println("업데이트 실패");
		}

		return "redirect:my/home";
	}

	@PostMapping("search")
	public String search(UserDTO keyword, Model model, HttpServletRequest req, Criteria cri) {
		String src = "/summernoteImage/";
		HttpSession session = req.getSession();
		String loginUser = (String) session.getAttribute("loginUser");

		UserDTO userid = service.getUserById(loginUser); // 키워드로 select한 결과를 리스트에 담아옴
		List<String> d_keyword_re = service.getd_keyword_reBykeyword(keyword);
//		  System.out.println("1. d_keyword_re :: "+d_keyword_re);

		if (d_keyword_re.isEmpty()) {
//			  System.out.println("첫번째 if들어옴(d_board에 keyword가 없음)");
//			  System.out.println("해당하는 기부 게시글이 없음 ");
			List<String> f_keyword_re = service.getf_keyword_reBykeyword(keyword);
//			  System.out.println("f_keyword_re :: "+f_keyword_re);

			if (f_keyword_re.isEmpty()) {
//				  System.out.println("해당하는 펀드 게시글이 없음");
//				  System.out.println("두번째 if문 들어옴(f_board에 keyword가 없음) ");
				return "user/my/activity_history";
			} else {
//				  System.out.println("f_board에 keyword가 있음 (f_board의 else시작)");
				List<String> F_boardNum = service.getF_boardNumByKeyword_re(userid);
//				  System.out.println("F_boardNum :: "+F_boardNum);
//				  게시판 번호로 펀딩 보드 제목을 불러옴
				List<String> F_boardTitle = service.getF_boardTitleByNum(F_boardNum);
//				  System.out.println("F_boardTitle :: "+F_boardTitle);
				List<String> F_boardOrgid = service.getF_boardOrgidBynum(F_boardNum);
//				  System.out.println("F_boardOrgid :: "+F_boardOrgid);
				List<String> f_systemName = service.getF_boardNumBynum(F_boardNum);
//				  System.out.println("f_systemName :: "+f_systemName);

				model.addAttribute("f_systemname", f_systemName);
				model.addAttribute("f_boardnum", F_boardNum);
				model.addAttribute("f_boardtitle", F_boardTitle);

				model.addAttribute("f_boardOrgid", F_boardOrgid);
//				  System.out.println("View딴으로 model전달됨");
				return "user/my/activity_history";

			}

		} else {
//			  System.out.println("d_board에 keyword가 있음 (d_board의 else시작)");
			List<String> d_boardNum = service.getD_boardNumByKeyword_re(d_keyword_re);
//			  System.out.println("d_boardNum :: "+d_boardNum);
			List<String> d_boardTitle = service.getD_boardTitleBynum(d_boardNum);
//			  System.out.println("d_boardTitle :: "+d_boardTitle);
			List<String> d_boardOrgid = service.getD_boardOrgidBynum(d_boardNum);
//			  System.out.println("d_boardOrgid :: "+d_boardOrgid);
			List<String> d_systemName = service.getSystemNameByBoardNum(d_boardNum);
//			  System.out.println("d_systemName :: "+d_systemName);
			model.addAttribute("d_boardnum", d_boardNum);
			model.addAttribute("keyword_re_list", d_keyword_re);
			model.addAttribute("d_boardtitle", d_boardTitle);
			model.addAttribute("d_boardOrgid", d_boardOrgid);
			model.addAttribute("d_systemname", d_systemName);
//			  System.out.println("View딴으로 model전달됨");
			return "user/my/activity_history";
		}
	}

	@PostMapping("addFollow")
	public ResponseEntity<String> addFollow(@RequestBody FollowDTO follow, HttpServletRequest req) {
		System.out.println("user/addFollow");
		HttpSession session = req.getSession();
		Object loginUser_temp = session.getAttribute("loginUser");
		if (loginUser_temp != null) {
			String loginUser = loginUser_temp.toString();
			follow.setUserid(loginUser);
			if (service.addFollow(follow)) {
				return new ResponseEntity<String>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

//		user_like page
	@GetMapping("my/User_Like")
	public String Usert_Like(UserDTO keyword, Model model, HttpServletRequest req) {
		String src = "/summernoteImage/";
		HttpSession session = req.getSession();
		String loginUser = (String) session.getAttribute("loginUser");
		System.out.println("loginUser :: " + loginUser);
		List<Map<String,Object>> d_likeinfo = service.getD_LikeInfoByUserid(loginUser);
		List<Map<String,Object>> f_likeinfo = service.getF_LikeInfoByUserid(loginUser);
		List<String> d_systemname = service.getD_systemName(loginUser);
		List<String> f_systemname = service.getF_systemName(loginUser);
		
		if(d_systemname == null || f_systemname == null) {
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("d_likeinfo", d_likeinfo);
			model.addAttribute("f_likeinfo", f_likeinfo);
			return "user/my/User_Like";
		}
		else if(d_systemname.equals(null)) {
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("d_likeinfo", d_likeinfo);
			model.addAttribute("f_likeinfo", f_likeinfo);
			model.addAttribute("f_systemname", src+f_systemname);
			return "user/my/User_Like";
		}
		else if(d_systemname.equals(null)) {
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("d_likeinfo", d_likeinfo);
			model.addAttribute("f_likeinfo", f_likeinfo);
			model.addAttribute("d_systemname", src+d_systemname);
			return "user/my/User_Like";
		}
		else {
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("d_likeinfo", d_likeinfo);
			model.addAttribute("f_likeinfo", f_likeinfo);
			model.addAttribute("f_systemname", src+f_systemname);
			model.addAttribute("d_systemname", src+d_systemname);
			return "user/my/User_Like";
		}
		/*
		 * model.addAttribute("loginUser", loginUser); model.addAttribute("d_likeinfo",
		 * d_likeinfo); model.addAttribute("f_likeinfo", f_likeinfo); return
		 * "user/my/User_Like";
		 */
	}

	@PostMapping("deleteLike")
	@ResponseBody // 반환된 객체가 JSON 형식으로 변환되어 클라이언트에게 응답하도록 한다.
	public Map<String, Object> delete_like(@RequestBody String c_board, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String loginUser = (String) session.getAttribute("loginUser"); // 세션에서 "loginUser"라는 속성을 문자열로 가져온다.

		// 로그인 사용자가 없을 경우 처리
		if (loginUser == null) {
			// 예: 적절한 에러 메시지를 반환
			Map<String, Object> response = new HashMap<>();
			response.put("success", false);
			response.put("message", "User not logged in.");
			return response;
		}

		System.out.println("c_board :: " + c_board);

		// 실제 좋아요 취소 로직을 여기에 추가
		// 예: 좋아요 데이터를 삭제하는 서비스 호출

		// 성공 응답
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("someValue", "좋아요가 취소되었습니다.");

		return response;
	}

	@GetMapping("my/delete_userinfo")
	public String deleteUser(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		session.getAttribute("loginUser");
		String loginUser = (String) session.getAttribute("loginUser");

		model.addAttribute("loginUser", loginUser);

		return "user/my/delete_userinfo";
	}

	@PostMapping("delete")
	public String delete(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		session.getAttribute("loginUser");
		String loginUser = (String) session.getAttribute("loginUser");

		/*
		 * if(service.deleteF_detail(loginUser)) { System.out.println("F_detail 삭제완료");
		 * } else { System.out.println("F_detail 정보가 없는 회원입니다"); }
		 * if(service.deleteD_detail(loginUser)) { System.out.println("D_detail 삭제완료");
		 * } else { System.out.println("D_detail 정보가 없는 회원입니다"); }
		 */
		if (service.deleteFollow(loginUser)) {
			System.out.println("Follow 삭제완료");
		} else {
			System.out.println("Follow 정보가 없는 회원입니다");
		}
		if (service.deleteLike(loginUser)) {
			System.out.println("Like 삭제완료");
		} else {
			System.out.println("Like 정보가 없는 회원입니다");
		}
		if (service.deleteReview(loginUser)) {
			System.out.println("Review 삭제완료");
		} else {
			System.out.println("Review 정보가 없는 회원입니다");
		}
		if (service.deleteD_payment(loginUser)) {
			System.out.println("D_payment 삭제완료");
		} else {
			System.out.println("D_payment 정보가 없는 회원입니다");
		}
		if (service.deleteF_payment(loginUser)) {
			System.out.println("F_payment 삭제완료");
		} else {
			System.out.println("F_payment 정보가 없는 회원입니다");
		}
		if (service.deleteS_payment(loginUser)) {
			System.out.println("S_payment 삭제완료");
		} else {
			System.out.println("S_payment 정보가 없는 회원입니다");
		}
		if (service.deleteUser(loginUser)) {
			System.out.println("모든정보 삭제완료");
			req.getSession().invalidate();

		}
		return "redirect:/";
	}

}
