package com.gl.givuluv.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.domain.dto.OrgapproveDTO;
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
	 @GetMapping("login")
	    public String loginPage(HttpSession session) {
	        if (session.getAttribute("loginUser") != null || 
	            session.getAttribute("loginSeller") != null || 
	            session.getAttribute("loginOrg") != null || 
	            session.getAttribute("loginManager") != null) {
	            // 이미 로그인된 경우 홈 페이지로 리다이렉트
	            return "redirect:/";
	        }
	        // 로그인 페이지로 이동
	        return "user/login";
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
	
	@PostMapping("approve")
	@ResponseBody
	public ResponseEntity<String> approve(@RequestBody Map<String, Object> payload) {
	    System.out.println(payload);
	    Object approveNum = payload.get("approveNum");
	    
	    System.out.println(approveNum);

	    if (approveNum instanceof String) {
	        String sname = (String) approveNum;
	        service.sellerApprove(sname);
	    } else if (approveNum instanceof Integer) {
	        int approvenum = (Integer) approveNum;
	        service.orgApprove(approvenum);
	    }

	    return ResponseEntity.ok("승인이 완료되었습니다!");
	}
	@PostMapping("approveCancel")
	@ResponseBody
	public ResponseEntity<String> approveCancel(@RequestBody Map<String, Object> payload) {
		System.out.println(payload);
		Object approveNum = payload.get("approveNum");
		System.out.println(approveNum);
		
		if (approveNum instanceof String) {
			String sname = (String) approveNum;
			service.sellerApproveCancel(sname);
		} else if (approveNum instanceof Integer) {
			int approvenum = (Integer) approveNum;
			service.orgApproveCancel(approvenum);
		}
		
		return ResponseEntity.ok("승인이 취소되었습니다!");
	}
	@GetMapping("orgApproveProfile")
	public String orgApproveProfile(int oapprovenum, Model model) {
		Map<String, Object> orgApproveProfile = service.orgApproveProfile(oapprovenum);
		model.addAttribute("profile", orgApproveProfile);
		return "org/orgApproveProfile";
		
	}
	@GetMapping("sellerApproveProfile")
	public String sellerApproveProfile(String sName, Model model) {
		Map<String, Object> sellerApproveProfile = service.sellerApproveProfile(sName);
		model.addAttribute("profile", sellerApproveProfile);
		return "seller/sellerApproveProfile";
		
	}
}

