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
import com.gl.givuluv.domain.dto.DPaymentDTO;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.OrgDTO;
import com.gl.givuluv.domain.dto.ReviewDTO;
import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.service.DBoardService;
import com.gl.givuluv.service.DPaymentService;
import com.gl.givuluv.service.FileService;
import com.gl.givuluv.service.OrgService;
import com.gl.givuluv.service.ReviewService;
import com.gl.givuluv.service.UserService;
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
	private UserService uservice;
	@Autowired
	private OrgService oservice;
	@Autowired
	private DBoardService dbservice;
	@Autowired
	private FileService fservice;
	@Autowired
	private DPaymentService pservice;
	@Autowired
	private ReviewService rservice;
	
	@GetMapping("dBoard")
	public String dBoardList(Model model) {
		List<DBoardDTO> list = dbservice.getList();
		List<ReviewDTO> rlist = rservice.getDReviewThree();
		
	    String src = "/summernoteImage/";
		//orgname을 Map타입에 저장
		Map<String, String> orgIdToNameMap = new HashMap<>();
			
		// list에서 각 DBoardDTO 객체의 orgid 값을 추출하여 orgname을 가져와 Map에 저장
		List<DBoardWithOrgNameDTO> resultList = new ArrayList<>();
		for (DBoardDTO dBoard : list) {
			String orgid = dBoard.getOrgid();
			String orgname = oservice.getOrgnameByOrgid(orgid);
			orgIdToNameMap.put(orgid, orgname);
			// 파일의 systemname 가져오기
			List<String> systemnameList = fservice.getSystemnameByBoardnum(dBoard.getDBoardnum() + "");
			if (systemnameList != null && !systemnameList.isEmpty()) {
				String systemname = src + systemnameList.get(0);
				//System.out.println("파일이름: " + systemname);
				resultList.add(new DBoardWithOrgNameDTO(dBoard, orgname, systemname));
			} else {
				resultList.add(new DBoardWithOrgNameDTO(dBoard, orgname));
			}
			//System.out.println(resultList);
			//System.out.println(orgname);
        }
		List<Map<String, Object>> reviewWithTitle = new ArrayList<>();
		for(ReviewDTO review : rlist) {
			int rboardnum = review.getConnectid();
			String boardtitle = dbservice.getDonation(rboardnum).getDTitle();
			List<String> systemnameList = fservice.getSystemnameByBoardnum(rboardnum + "");
			if (systemnameList != null && !systemnameList.isEmpty()) {
				String systemname = src + systemnameList.get(0);
				Map<String, Object> reviewDetail = new HashMap<>();
		        reviewDetail.put("review", review);
		        reviewDetail.put("boardtitle", boardtitle);
		        reviewDetail.put("systemname", systemname);
		        reviewWithTitle.add(reviewDetail);
			} else {
			}
		}
		//오늘날 기부한 금액 총합  
		int todayCost = pservice.getTodayDonationCost(); 
		//오늘날 기부한 사람들 
		int todayDonationPeople = pservice.getTodayDonationPeople();
		
		/* List<FileDTO> dfile = fservice.getFileByTypeisD(); */
		model.addAttribute("list", resultList);
		model.addAttribute("todayCost", todayCost);
		model.addAttribute("todayDonationPeople", todayDonationPeople);
		model.addAttribute("reviewWithTitle", reviewWithTitle);
		return "donation/dBoard";
	}
	@GetMapping("donationView")
	public String view(@RequestParam("dBoardnum") int dBoardnum, Model model, HttpServletRequest req) {
		List<ReviewDTO> rlist = rservice.getReviewList(dBoardnum);
		int reviewCnt = rservice.getReviewCnt(dBoardnum);
		List<Map<String, Object>> rlistWithNicknames = new ArrayList<>();
		for (ReviewDTO review : rlist) {
		     String nickname = uservice.getNicknameByUserId(review.getUserid());
		     Map<String, Object> reviewWithNickname = new HashMap<>();
		     reviewWithNickname.put("review", review);
		     reviewWithNickname.put("nickname", nickname);
		     rlistWithNicknames.add(reviewWithNickname);
		}
		HttpSession session = req.getSession();
		String loginUser = (String)session.getAttribute("loginUser");
		String loginNickname = uservice.getNicknameByUserId(loginUser);
		System.out.println(rlistWithNicknames);
		DBoardDTO dboard = dbservice.getDonation(dBoardnum);
		String orgname = oservice.getOrgnameByOrgid(dboard.getOrgid());
		String category = oservice.getCategoryByOrgid(dboard.getOrgid());
		int d_cost = pservice.getTotalCostByBoardnum(dBoardnum);
	    int totalCost = pservice.getTotalCostByOrgid(dboard.getOrgid());
	    int RdonationCnt = pservice.getRdonationCntByType(dBoardnum);
	    System.out.println(d_cost); //152000
	    System.out.println(dboard.getTargetAmount()); //9900000
	    double percentage = (double) d_cost / dboard.getTargetAmount() * 100;
	    System.out.println(d_cost);
	    System.out.println("퍼센트= "+percentage);
	    
		System.out.println("orgname: "+orgname);
		model.addAttribute("dboard",dboard);
		model.addAttribute("d_cost", d_cost);
		model.addAttribute("totalCost", totalCost);
		model.addAttribute("orgname", orgname);
		model.addAttribute("orgcategory", category);
		model.addAttribute("RdonationCnt", RdonationCnt);
		model.addAttribute("percentage", String.format("%.1f", percentage));
		model.addAttribute("review", rlistWithNicknames);
		model.addAttribute("reviewCnt", reviewCnt);
		model.addAttribute("loginNickname", loginNickname);
		
		// D-day 계산하여 모델에 추가
	    LocalDate currentDate = LocalDate.now();
	    LocalDate endDate = LocalDate.parse(dboard.getDEnddate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    long daysUntilEnd = ChronoUnit.DAYS.between(currentDate, endDate);
	    model.addAttribute("daysUntilEnd", daysUntilEnd);

		return "donation/donationView";
	}
	@GetMapping("write")
	public String write(HttpServletRequest req, Model model) {
		String url = "/";
		HttpSession session = req.getSession();
		String loginOrg = session.getAttribute("loginOrg").toString();
		if(dbservice.isApproveOrg(loginOrg) == 1) {
			return "donation/write";
		}
		else if(dbservice.isApproveOrgX(loginOrg) == 1){
			System.out.println("승인이 필요한 사회단체입니다.");
			model.addAttribute("alertMessage", "단체 승인 대기 중입니다.\n승인 후 기부 글쓰기 가능합니다.");
			model.addAttribute("redirectUri", url);
			return "store/storeMassege";
		}else {
			url="/org/register";
			model.addAttribute("alertMessage", "단체 가입 후 글쓰기가 가능합니다.");
			model.addAttribute("redirectUri", url);
			return "store/storeMassege";
		}
	}
    @GetMapping("pay")
    public String pay(@RequestParam("dBoardnum") int dBoardnum, Model model, HttpServletRequest req) {
    	DBoardDTO dboard = dbservice.getDonation(dBoardnum);
    	String orgname = oservice.getOrgnameByOrgid(dboard.getOrgid());
    	HttpSession session = req.getSession();
		String loginUser = (String)session.getAttribute("loginUser");
		String loginSeller = (String)session.getAttribute("loginSeller");
		String loginOrg = (String)session.getAttribute("loginOrg");
		System.out.println(loginUser);
		if(loginUser == null && loginSeller == null && loginOrg == null) {
			model.addAttribute("loginMessage", "로그인 후 이용해주세요");
	        return "user/login";
		}
		else if(loginUser == null && (loginSeller != null || loginOrg != null)) {
	        return "redirect:/donation/donationView?dBoardnum="+dBoardnum;
		}
			model.addAttribute("dboard",dboard);
			model.addAttribute("orgname", orgname);
			return "donation/donationPay";			
    }
    @GetMapping("regularPay")
    public String regularPay(@RequestParam("dBoardnum") int dBoardnum, Model model) {
    	DBoardDTO dboard = dbservice.getDonation(dBoardnum);
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
    
    @GetMapping("getCategoryItems")
    public @ResponseBody List<DBoardWithOrgNameDTO> getCategoryItems(@RequestParam String orgcategory) {
        String src = "/summernoteImage/";
        List<DBoardDTO> items = dbservice.getItemsByCategory(orgcategory);
        List<DBoardWithOrgNameDTO> resultList = new ArrayList<>();
        if ("전체".equals(orgcategory)) {
            List<DBoardDTO> list = dbservice.getList();
            Map<String, String> orgIdToNameMap = new HashMap<>();
            
            for (DBoardDTO dBoard : list) {
                String orgid = dBoard.getOrgid();
                String orgname = oservice.getOrgnameByOrgid(orgid);
                orgIdToNameMap.put(orgid, orgname);
                // 파일의 systemname 가져오기
                List<String> systemnameList = fservice.getSystemnameByBoardnum(dBoard.getDBoardnum()+"");
                if (systemnameList != null && !systemnameList.isEmpty()) {
                    String systemname = src + systemnameList.get(0); 
                    System.out.println("파일이름: "+systemname);
                    resultList.add(new DBoardWithOrgNameDTO(dBoard, orgname, systemname));
                } else {
                    resultList.add(new DBoardWithOrgNameDTO(dBoard, orgname));
                }
                System.out.println(resultList);
                System.out.println(orgname);
            }
        } 
        //전체 말고 다른 카테고리 선택했을 때
        else {
            for (DBoardDTO dBoard : items) {
                String orgname = oservice.getOrgnameByOrgid(dBoard.getOrgid());
                List<String> systemnameList = fservice.getSystemnameByBoardnum(dBoard.getDBoardnum()+"");
                if (systemnameList != null && !systemnameList.isEmpty()) {
                    String systemname = src + systemnameList.get(0); 
                    System.out.println("파일이름: "+systemname);
                    resultList.add(new DBoardWithOrgNameDTO(dBoard, orgname, systemname));
                } else {
                    resultList.add(new DBoardWithOrgNameDTO(dBoard, orgname));
                }
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
    public String write(DBoardDTO dBoard, String filenames, MultipartFile thumbnail, HttpServletRequest req) throws Exception{
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
       // 세션이랑 orgid 맞는지 확인
       HttpSession session = req.getSession();
       String orgId = session.getAttribute("loginOrg").toString();
       if(dBoard.getOrgid().equals(orgId)) {
          System.out.println("세션검사완료");
          if(dbservice.regist(dBoard, filenames, thumbnail)) {
             System.out.println("게시글 등록완료");
             int dBoardnum = dbservice.getDonationLastBoardnumByOrgid(dBoard.getOrgid());
             System.out.println(dBoardnum);
             return "redirect:/donation/donationView?dBoardnum="+dBoardnum;
          }
       }
       System.out.println("실패");
       return "donation/dBoard";
    }
    @GetMapping("receipt")
	public String dReceipt(@RequestParam int paymentnum, Model model, HttpServletRequest req) {
    	HttpSession session = req.getSession();
		String userid = (String)session.getAttribute("loginUser");
    	DPaymentDTO payment = pservice.getLastPaymentById(userid);
    	DBoardDTO dboard = dbservice.getDonation(payment.getDBoardnum());
    	String orgname = oservice.getOrgnameByOrgid(payment.getOrgid());
    	UserDTO user = uservice.getUserById(userid);
    	LocalDate today = LocalDate.now();
        LocalDate nextMonthFirstDay = today.plusMonths(1).withDayOfMonth(1);
        LocalDate firstFriday = nextMonthFirstDay.with(DayOfWeek.FRIDAY);

        if (nextMonthFirstDay.getDayOfWeek().getValue() > DayOfWeek.FRIDAY.getValue()) {
            firstFriday = firstFriday.plusWeeks(1);
        }
        
    	model.addAttribute("payment", payment);
    	model.addAttribute("orgname", orgname);
    	model.addAttribute("dboard", dboard);
    	model.addAttribute("user", user);
    	model.addAttribute("nextpayday", nextMonthFirstDay);
    	
	    return "donation/dReceipt";
	}
    @GetMapping("Rreceipt")
    public String dRReceipt(@RequestParam int paymentnum, Model model, HttpServletRequest req) {
    	HttpSession session = req.getSession();
    	String userid = (String)session.getAttribute("loginUser");
    	DPaymentDTO payment = pservice.getLastPaymentById(userid);
    	DBoardDTO dboard = dbservice.getDonation(payment.getDBoardnum());
    	String orgname = oservice.getOrgnameByOrgid(payment.getOrgid());
    	UserDTO user = uservice.getUserById(userid);
    	LocalDate today = LocalDate.now();
    	LocalDate nextMonthFirstDay = today.plusMonths(1).withDayOfMonth(1);
    	LocalDate firstFriday = nextMonthFirstDay.with(DayOfWeek.FRIDAY);
    	
    	if (nextMonthFirstDay.getDayOfWeek().getValue() > DayOfWeek.FRIDAY.getValue()) {
    		firstFriday = firstFriday.plusWeeks(1);
    	}
    	
    	model.addAttribute("payment", payment);
    	model.addAttribute("orgname", orgname);
    	model.addAttribute("dboard", dboard);
    	model.addAttribute("user", user);
    	model.addAttribute("nextpayday", nextMonthFirstDay);
    	
    	return "donation/dRReceipt";
    }
}
