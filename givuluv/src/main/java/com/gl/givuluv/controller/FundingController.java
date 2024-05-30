package com.gl.givuluv.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.service.FBoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/funding/*")
public class FundingController {
	
	@Autowired
	private FBoardService fbservice;
	
	@GetMapping("fBoard")
	public String getFBoard(Model model) {
		List<Map<String, Object>> result = fbservice.getFundingList();
		if(result != null) {
			model.addAttribute("fundingList", result);
			return "funding/fBoard";
		}else {
			return "funding/fBoard";
		}
	}
	
	@GetMapping("write")
	public void write() {}
	
	@PostMapping("write")
	public String regist(Model model, FBoardDTO fBoard,
			@ModelAttribute("productList") ProductDTO productList, String filenames, MultipartFile thumbnail,
			HttpServletRequest req) throws Exception {
		// 경로
		System.out.println("Post : store/write");
		// 파라미터 출력
		System.out.println(fBoard.getFTitle());
		System.out.println(fBoard.getFContent());
		System.out.println(fBoard.getFEnddate());
		System.out.println(fBoard.getTargetAmount());
		System.out.println(fBoard.getOrgid());
		List<ProductDTO> products = productList.getProductList();
		for (ProductDTO product : products) {
			System.out.println(product.getProductname());
			System.out.println(product.getCost());
			System.out.println(product.getPAmount());
		}
		System.out.println("파일이름들 : "+filenames);
		// 세션 검사
		HttpSession session = req.getSession();
		String sessionOrgId = session.getAttribute("loginOrg").toString();
		if (sessionOrgId.equals(fBoard.getOrgid())) {
			if (fbservice.regist(model, fBoard, products, filenames, thumbnail)) {
				System.out.println("성공");
			}
		}
		List<Map<String, Object>> result = fbservice.getFundingList();
		if(result != null) {
			model.addAttribute("fundingList", result);
			return "funding/fBoard";
		}else {
			return "funding/fBoard";
		}
	}
	
	@GetMapping("fundingView")
	public String fundingView(int fBoardnum, Model model) {
		Map<String, Object> fundingDetail = fbservice.getFundingDetail(fBoardnum);
		model.addAttribute("fundingDetail", fundingDetail);
		return "/funding/fundingView";
	}
	@GetMapping("sortedFundingList")
	@ResponseBody
	public List<Map<String, Object>> sortedFundingList(@RequestParam String sortType) {
		if(sortType.equals("최신순")) {
			List<Map<String, Object>> result = fbservice.getFundingList();
			return result;
		}else if(sortType.equals("참여금액순")) {
			List<Map<String, Object>> result = fbservice.getParticipationCost();
			return result;
		}else if(sortType.equals("참여율순")) {
			List<Map<String, Object>> result = fbservice.getParticipationRate();
			return result;
		}else if(sortType.equals("종료임박순")) {
			List<Map<String, Object>> result = fbservice.getFundingEnddateList();
			return result;
		}
		return null;
	}
	@GetMapping("fundingPay")
	public String fundingPay(@RequestParam("fBoardnum") int fBoardnum, @RequestParam("cost") int cost, @RequestParam("fundingCnt") 
	int fundingCnt, @RequestParam("productname") String funding, @RequestParam("orgname") String orgname, HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String userid = (String)session.getAttribute("loginUser");
		UserDTO user = fbservice.getUserById(userid);
		FBoardDTO fboard = fbservice.getFundingByFBoardnum(fBoardnum);
		int orderCost = 0;
		int getBonus = 0;
		if(cost < 50000 && user.getAddr().contains("제주")) {
			orderCost += 7000;
		}
		else if(cost >= 50000 && user.getAddr().contains("제주")) {
			orderCost += 4000;  
		}
		else if(cost < 50000) {
			orderCost += 3000;
		}
		getBonus = (int) ((cost+orderCost)*0.03);
		 
		model.addAttribute("fboard", fboard);
		model.addAttribute("cost", cost);
		model.addAttribute("orderCost", orderCost);
		model.addAttribute("getBonus", getBonus);
		model.addAttribute("productname", funding);
		model.addAttribute("fundingCnt", fundingCnt);
		model.addAttribute("orgname", orgname);
		model.addAttribute("user", user);
		return "funding/fundingPay";
	}
	
	@GetMapping("fReceipt")
	public String fReceipt(int paymentnum) {
		System.out.println(paymentnum);
		return "funding/fReceipt";
	}
}
