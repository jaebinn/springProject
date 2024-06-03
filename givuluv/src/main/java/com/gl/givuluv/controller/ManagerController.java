package com.gl.givuluv.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.service.ManagerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager/*")
public class ManagerController {
	
	@Autowired
	private ManagerService service;
	
	@GetMapping("checkManagername")
	 public ResponseEntity<String> checkManagername(@RequestParam String managername) {
        try {
            boolean isAvailable = service.checkManagername(managername);
            return ResponseEntity.ok(isAvailable ? "O" : "X");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
	@PostMapping("login")
	public String managerLogin(String managerid, String managerpw, HttpServletRequest req) {
		HttpSession session = req.getSession();
        if(service.login(managerid, managerpw)) {
            System.out.println(managerid + " 로그인됨");
            session.setAttribute("loginManager", managerid);
            return "redirect:/";
        } else {
            System.out.println("로그인 실패");
            return "user/login";
        }
	}
	@GetMapping("checkManagerId")
	@ResponseBody
	public String checkManagerId(String managerid) {
		if(service.checkManagerId(managerid)) {
			return "O";
		}
		else {
			return "X";
		}
	}
	@GetMapping("getApproveCnt")
	@ResponseBody
	public int getApproveCnt() {
		int approveTotalCnt = service.getNotApproveCnt();
		return approveTotalCnt;
	}
	
	@GetMapping("getApproveInfo")
	@ResponseBody
	public List<Map<String, Object>> getApproveInfo() {
		List<Map<String, Object>> orgApproveInfo = service.getApproveInfo();
		return orgApproveInfo;
	}
}

