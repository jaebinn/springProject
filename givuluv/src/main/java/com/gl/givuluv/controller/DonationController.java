package com.gl.givuluv.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.givuluv.domain.dto.DBoardDTO;
import com.gl.givuluv.domain.dto.DBoardWithOrgNameDTO;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.service.DBoardService;
import com.gl.givuluv.service.OrgService;
import com.google.gson.Gson;

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
            System.out.println(orgid);
            String orgname = oservice.getOrgnameByOrgid(orgid);
            System.out.println(orgname);
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
		String orgname = oservice.getOrgnameByOrgid(dboard.getOrgid());
		String category = oservice.getCategoryByOrgid(dboard.getOrgid());
		System.out.println("orgname: "+orgname);
		model.addAttribute("dboard",dboard);
		model.addAttribute("orgname", orgname);
		model.addAttribute("orgcategory", category);
		
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
    	String orgname = oservice.getOrgnameByOrgid(dboard.getOrgid());
		model.addAttribute("dboard",dboard);
		model.addAttribute("orgname", orgname);
    	return "donation/donationPay";
    }
    @GetMapping("regularPay")
    public String regularPay(@RequestParam("dBoardnum") int dBoardnum, Model model) {
    	DBoardDTO dboard = dservice.getDonation(dBoardnum);
    	String orgname = oservice.getOrgnameByOrgid(dboard.getOrgid());
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
    
    //카테고리 정렬
    @GetMapping("getCategoryItems")
    public @ResponseBody List<DBoardWithOrgNameDTO> getCategoryItems(@RequestParam String orgcategory) {
        List<DBoardDTO> items = dservice.getItemsByCategory(orgcategory);
        List<DBoardWithOrgNameDTO> resultList = new ArrayList<>();
        if ("전체".equals(orgcategory)) {
            List<DBoardDTO> list = dservice.getList();
            Map<String, String> orgIdToNameMap = new HashMap<>();

            for (DBoardDTO dBoard : list) {
                String orgid = dBoard.getOrgid();
                String orgname = oservice.getOrgnameByOrgid(orgid);
                orgIdToNameMap.put(orgid, orgname);
                resultList.add(new DBoardWithOrgNameDTO(dBoard, orgname));
                System.out.println(orgname);
            }
        } else {
            for (DBoardDTO dBoard : items) {
                String orgname = oservice.getOrgnameByOrgid(dBoard.getOrgid());
                resultList.add(new DBoardWithOrgNameDTO(dBoard, orgname));
                System.out.println(orgname);
                System.out.println(resultList);
            }

            if (items == null || items.isEmpty()) {
                System.out.println("등록된 게시글이 없습니다.");
                return Collections.emptyList();
            }
            System.out.println("글 개수: " + items.size());
        }
        System.out.println("과연: "+resultList);
        return resultList;
    }
    
    @PostMapping("write")
	public String write(DBoardDTO dBoard, String filenames, MultipartFile thumbnail) throws Exception{
		// 경로
		System.out.println("Post : donation/write");
		// 파라미터 출력
		System.out.println(dBoard.getDTitle());
		System.out.println(dBoard.getDContent());
		System.out.println(dBoard.getDEnddate());
		System.out.println(dBoard.getTargetAmount());
		System.out.println(dBoard.getOrgid());
		System.out.println(filenames);
		System.out.println(thumbnail.getOriginalFilename());
		// 세션이랑 orgid 맞는지 확인 유효성 검사 해야함.
//		if(dBoard.getOrgid() == sessionID)
		
		if(dservice.regist(dBoard, filenames, thumbnail)) {
			int dBoardnum = dservice.getDonationLastBoardnumByOrgid(dBoard.getOrgid());
			System.out.println(dBoardnum);
			return "redirect:/donation/donationView?dBoardnum="+dBoardnum;
		}
		else {
			return "donation/dBoard";
		}
	}
	

}
