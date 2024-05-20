package com.gl.givuluv.controller;

import java.time.DayOfWeek;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.service.DBoardService;
import com.gl.givuluv.service.OrgService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/donation/*")
public class DonationController {
	@Autowired
	private DBoardService dservice;
	@Autowired
	private OrgService oservice;
	/* private FileSerivce fservice; */
	
	
	@GetMapping("dBoard")
	public String dBoardList(Model model) {
		List<DBoardDTO> list = dservice.getList();
		System.out.println(list);
	    
		Map<String, String> orgIdToNameMap = new HashMap<>();

		// list에서 각 DBoardDTO 객체의 orgid 값을 추출하여 orgname을 가져와 Map에 저장
		for (DBoardDTO dBoard : list) {
            String orgid = dBoard.getOrgid();
            String orgname = oservice.getOrgnameByOrgid(orgid);
            orgIdToNameMap.put(orgid, orgname);
        }

		/* List<FileDTO> dfile = fservice.getFileByTypeisD(); */
		model.addAttribute("list", list);
		model.addAttribute("orgIdToNameMap", orgIdToNameMap);
		/* model.addAttribute("dfile", dfile); */
		return "donation/dBoard";
	}
	@GetMapping("donationView")
	public String view(@RequestParam("dBoardnum") int dBoardnum, Model model) {
		DBoardDTO dboard = dservice.getDonation(dBoardnum);
		String orgname = dservice.getOrgnameBynum(dBoardnum);
		model.addAttribute("dboard",dboard);
		model.addAttribute("orgname", orgname);
		
		// D-day 계산하여 모델에 추가
	    LocalDate currentDate = LocalDate.now();
	    LocalDate endDate = LocalDate.parse(dboard.getDEnddate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    long daysUntilEnd = ChronoUnit.DAYS.between(currentDate, endDate);
	    model.addAttribute("daysUntilEnd", daysUntilEnd);

		
		return "donation/donationView";
	}
	@GetMapping("write")
	public String write() {
	    return "donation/write";
	}
    @GetMapping("pay")
    public String pay(@RequestParam("dBoardnum") int dBoardnum, Model model) {
    	DBoardDTO dboard = dservice.getDonation(dBoardnum);
		String orgname = dservice.getOrgnameBynum(dBoardnum);
		model.addAttribute("dboard",dboard);
		model.addAttribute("orgname", orgname);
    	return "donation/donationPay";
    }
    @GetMapping("regularPay")
    public String regularPay(@RequestParam("dBoardnum") int dBoardnum, Model model) {
    	DBoardDTO dboard = dservice.getDonation(dBoardnum);
		String orgname = dservice.getOrgnameBynum(dBoardnum);
		// 현재 날짜에서 한 달을 더한 날짜 계산
		LocalDate today = LocalDate.now();
        LocalDate nextMonthFirstDay = today.plusMonths(1).withDayOfMonth(1);
        LocalDate firstFriday = nextMonthFirstDay.with(DayOfWeek.FRIDAY);

        if (nextMonthFirstDay.getDayOfWeek().getValue() > DayOfWeek.FRIDAY.getValue()) {
            firstFriday = firstFriday.plusWeeks(1);
        }

        String formattedNextPayDay = firstFriday.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        
    	model.addAttribute("dboard",dboard);
    	model.addAttribute("orgname", orgname);
    	model.addAttribute("nextPayDay", formattedNextPayDay);
    	return "donation/donationRegularPay";
    }
    
}
