package com.gl.givuluv.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.domain.dto.DBoardWithFile;
import com.gl.givuluv.domain.dto.DBoardWithOrgNameDTO;
import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.service.DBoardService;
import com.gl.givuluv.service.DPaymentService;
import com.gl.givuluv.service.FileService;
import com.gl.givuluv.service.OrgService;
import com.gl.givuluv.service.SBoardService;

@Controller
@RequestMapping("/index/*")
public class IndexController {
	@Autowired
	private DBoardService dbservice;
	@Autowired
	private OrgService oservice;
	@Autowired
	private SBoardService sservice;
	@Autowired
	private FileService fservice;
	@Autowired
	private DPaymentService pservice;
	
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
				String systemname = src + systemnameList.get(1);
				LocalDate currentDate = LocalDate.now();
			    LocalDate endDate = LocalDate.parse(dboard.getDEnddate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			    int deadline = (int) ChronoUnit.DAYS.between(currentDate, endDate);
				System.out.println(deadline);
				System.out.println(percentage);
				System.out.println("파일이름: " + systemname);
				resultList.add(new DBoardWithFile(dboard, deadline, percentage, systemname));
			} else {
				System.out.println("없을리가");
			}
		}
		System.out.println(resultList);
		return resultList;
	}
	
	/*
	 * @GetMapping("getFundingInfo") public List<FBoardWithFile> getDonationInfo(){
	 * 
	 * }
	 */
}
