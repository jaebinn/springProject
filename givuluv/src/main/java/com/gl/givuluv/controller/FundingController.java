package com.gl.givuluv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.service.FBoardService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/funding/*")
public class FundingController {
	
	@Autowired
	private FBoardService fbservice;
	
	@GetMapping("fBoard")
	public void replace() {}
	
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
		System.out.println("실패");
		return "store/sBoard";
	}
}
