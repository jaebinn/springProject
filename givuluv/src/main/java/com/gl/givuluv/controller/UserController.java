package com.gl.givuluv.controller;

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
import com.gl.givuluv.service.MailSendService;
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
    private final MailSendService mailService;
    
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
        req.getSession().invalidate();
        return "redirect:/";
    }
    @PostMapping("login")
    public String login(String userid, String userpw, HttpServletRequest req) {
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
    
}
