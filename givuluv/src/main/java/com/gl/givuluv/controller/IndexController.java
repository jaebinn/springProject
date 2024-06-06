package com.gl.givuluv.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.domain.dto.DBoardWithFile;
import com.gl.givuluv.domain.dto.DBoardWithOrgNameDTO;
import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.domain.dto.FBoardWithFile;
import com.gl.givuluv.service.DBoardService;
import com.gl.givuluv.service.DPaymentService;
import com.gl.givuluv.service.FBoardService;
import com.gl.givuluv.service.FPaymentService;
import com.gl.givuluv.service.FileService;
import com.gl.givuluv.service.OrgService;
import com.gl.givuluv.service.ProductService;
import com.gl.givuluv.service.SBoardService;
import com.gl.givuluv.service.StoreService;

@Controller
@RequestMapping("/index/*")
public class IndexController {
	@Autowired
	private DBoardService dbservice;
	@Autowired
	private FBoardService fbservice;
	@Autowired
	private SBoardService sbservice;
	@Autowired
	private FileService fservice;
	@Autowired
	private DPaymentService pservice;
	@Autowired
	private FPaymentService fpservice;
	@Autowired
	private ProductService prservice;
	
	@GetMapping("getTodayCnt")
	@ResponseBody
	public Map<String, Object> getTodayCnt() {
		List<DPaymentDTO> plist = pservice.getDPayment();
		int d_totalPeople = pservice.getDonationTotalPeople();
		int d_totalCost = pservice.getDonationTotalCost();
		System.out.println(d_totalPeople);
		System.out.println(d_totalCost);
		int f_totalPeople = fpservice.getFundingTotalPeople();
		int f_totalCost = fpservice.getFundingTotalCost();
		System.out.println("펀딩사람: "+f_totalPeople);
		System.out.println("펀딩금액: "+f_totalCost);
		
		 Map<String, Object> result = new HashMap<>();
		 result.put("donationTotalPeople", d_totalPeople);
		 result.put("donationTotalCost", d_totalCost);
		 result.put("fundingTotalPeople", f_totalPeople);
		 result.put("fundingTotalCost", f_totalCost);
		
		return result;
	}
	
	@GetMapping("getDonationInfo")
	@ResponseBody
	//인덱스에 기부 정보 띄울것
	public List<DBoardWithFile> getDonationInfo() {
		List<DBoardDTO> dlist = dbservice.getList();
		List<DPaymentDTO> plist = pservice.getDPayment();
		String src = "/summernoteImage/";
		
		List<DBoardWithFile> resultList = new ArrayList<>();
		for (DBoardDTO dboard : dlist) {
			// 파일의 systemname 가져오기
			List<String> systemnameList = fservice.getSystemnameByBoardnum(dboard.getDBoardnum() + "");
			if (systemnameList != null && !systemnameList.isEmpty()) {
				double percentage = (double)dboard.getSaveMoney() / (double)dboard.getTargetAmount() * 100;
				String systemname = src + systemnameList.get(0);
				LocalDate currentDate = LocalDate.now();
			    LocalDate endDate = LocalDate.parse(dboard.getDEnddate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			    int deadline = (int) ChronoUnit.DAYS.between(currentDate, endDate);
				System.out.println(deadline);
				System.out.println(percentage);
				System.out.println("파일이름: " + systemname);
				resultList.add(new DBoardWithFile(dboard, deadline, percentage, systemname));
			} else {
				
			}
		}
		return resultList;
	}
	
	
	@GetMapping("getFundingInfo") 
	@ResponseBody
	public List<Map<String, Object>> getFundingInfo(){
		List<Map<String, Object>> result = fbservice.getFundingList();
		System.out.println("펀딩: "+result);
		return result;
	}
	@GetMapping("getStoreInfo") 
	@ResponseBody
	public List<Map<String, Object>> getStoreInfo(){
		List<Map<String, Object>> result = sbservice.getMSBoardList();
		System.out.println("가게:"+result);
		return result;
	}
	

	
}
