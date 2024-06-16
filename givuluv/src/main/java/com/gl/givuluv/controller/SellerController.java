package com.gl.givuluv.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.Criteria;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.PageDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.QnaDTO;
import com.gl.givuluv.domain.dto.ReviewDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SellerDTO;
import com.gl.givuluv.domain.dto.SinfoDTO;
import com.gl.givuluv.domain.dto.StoreDTO;
import com.gl.givuluv.service.FileService;
import com.gl.givuluv.service.MailSendService;
import com.gl.givuluv.service.SellerService;
import com.gl.givuluv.service.StoreService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller/*")

public class SellerController {

   @Autowired
   private SellerService service;
   @Autowired
   private FileService fservice;
   @Autowired
   private StoreService sservice;
   
   private final MailSendService mailService;
   
   @Autowired 
   public SellerController(MailSendService mailService) {
      this.mailService = mailService;
   }
   //판매자 회원가입 =================================================================
   
   //get방식 일때 (기존 UserController 방식을 인용했습니다) 
   @GetMapping("join")
   public void replace() {}
   
   //post방식 일때 (기존 UserController 방식을 인용했습니다) 
   @PostMapping("join")
   public String join(SellerDTO seller, MultipartFile[] files,HttpServletResponse resp) throws Exception {
      //회원가입 처리
      if(service.join(seller, files)) {
         Cookie cookie = new Cookie("join_sellerid", seller.getSellerid());
         cookie.setPath("/");
         cookie.setMaxAge(60);
         resp.addCookie(cookie);
      }
      else {
         //
      }
      return "user/login";
   }
   //판매자 회원가입 끝 ================================================================
   
   //이메일 인증 =====================================================================
   //이메일 인증(기존 UserController 방식을 인용했습니다)
      @GetMapping("checkIdAndEmail")
      @ResponseBody
      //판매자는 닉네임이 없어서 'checkNickAndEmail' 을 'checkIdAndEmail'로 변경 후 SellerService에 추가하였습니다.
      public ResponseEntity<String> mailCheck(@RequestParam String sellerid, @RequestParam String email) {
         if(service.checkIdAndEmail(sellerid, email)) {
            System.out.println("이메일 인증 요청이 들어옴!");
            System.out.println("이메일 인증 이메일 : " + email);
            String result = mailService.joinEmail(email);
               return ResponseEntity.ok(result);         
         } 
         else {
            System.out.println("일치하는 [판매자] 회원이 없습니다.");
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 판매자 회원이 없습니다.");      
         }
      }
   //이메일 인증 끝 ==================================================================
         
   //아이디 중복검사 =================================================================
      //아이디 중복검사
      @GetMapping("checkId")
      @ResponseBody
      public String checkId(String sellerid) {
         if(service.checkId_duplication(sellerid)) {
            System.out.println("O");
            return "O";
         }
         else { 
            System.out.println("X");
            return "X";
         }
      }   
   //아이디 중복검사 끝 ==============================================================
   
   //로그인 시작 ===================================================================
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
      @GetMapping("logout")
      public String logout(HttpServletRequest req) {
         req.getSession().invalidate();
         return "redirect:/";
      }
    //MDM
      
      @PostMapping("login")
      public String login(String sellerid, String sellerpw, String type, HttpServletRequest req, Model model) {
          HttpSession session = req.getSession();
          if(service.login(sellerid, sellerpw)) {
              System.out.println(sellerid + " 로그인됨");
              session.setAttribute("loginSeller", sellerid);
              //스토어 승인 또는 등록 확인
              char signupcheck = service.checkStoreSignup(sellerid);
              String url;
              if(signupcheck == '-') {
            	url = "/";
      			model.addAttribute("alertMessage", "스토어 승인 대기 중입니다.");
      			model.addAttribute("redirectUri", url);
      			return "store/storeMassege";
              }
              else if(signupcheck == 'o') {
            	if(service.storeInfoCheck(sellerid)) {
            		//승인을 받은 후 스토어 등록을 했다면
            		return "redirect:/";
            	}
            	else {
            		//승인을 받은 후 스토어 등록을 안했다면
            		url = "/store/storewrite";
          			model.addAttribute("alertMessage", "스토어 등록 후 이용가능합니다.");
          			model.addAttribute("redirectUri", url);
          			return "store/storeMassege";
            	}
              }
              else{
            	  url = "/";
        			model.addAttribute("alertMessage", "스토어 승인신청 해주세요.");
        			model.addAttribute("redirectUri", url);
        			return "store/storeMassege";  
              }
              
          } else {
             model.addAttribute("sellerLoginErr", "로그인 실패");
             model.addAttribute("type", type);
             System.out.println("로그인 실패");
             return "user/login";
          }
      }
      @GetMapping("my/home")
      public String SellerMyHome(String sellerid, HttpServletRequest req, Model model) {
    	HttpSession session = req.getSession();
		String loginSeller = (String)session.getAttribute("loginSeller");
		String systemname = service.getSellerProfile(loginSeller);
		System.out.println(systemname);
		model.addAttribute("systemname", systemname);
		return "seller/my/home";
		 
		 //잠시 주석처리!!
		 /* char check = sservice.checkStoreBySellerid(loginSeller);
		 * 
		 * String url;
		 * 
		 * if(check == 'o') { if(service.storeInfoCheck(loginSeller)) { //승인을 받은 후 스토어
		 * 등록을 했다면 //html정해지면 바꿔주기 return "seller/my/home"; } else { //승인을 받은 후 스토어 등록을
		 * 안했다면 url = "/store/storewrite"; model.addAttribute("alertMessage",
		 * "스토어 등록 후 이용가능합니다."); model.addAttribute("redirectUri", url); return
		 * "store/storeMassege"; } } else if(check == '-'){ url = "/";
		 * model.addAttribute("alertMessage", "스토어 승인 대기 중입니다.");
		 * model.addAttribute("redirectUri", url); return "store/storeMassege"; } else {
		 * url = "/store/storeSignup"; model.addAttribute("alertMessage",
		 * "스토어 승인 신청 후 이용가능합니다.."); model.addAttribute("redirectUri", url); return
		 * "store/storeMassege"; }
		 */
          
       }
      
      @GetMapping("homeIncome") 
      @ResponseBody
      public List<Map<String, Object>> getHomeIncome(HttpServletRequest req){
         HttpSession session = req.getSession();
         String sellerid = (String)session.getAttribute("loginSeller");
         
         List<Map<String, Object>> result = service.getTotalIncome(sellerid);
         System.out.println("getTotalIncome : "+result);
         return result;
      }
      
      @GetMapping("homeReview") 
      @ResponseBody
      public List<Map<String, Object>> getHomeReview(HttpServletRequest req){
         HttpSession session = req.getSession();
         String sellerid = (String)session.getAttribute("loginSeller");
         
         List<Map<String, Object>> result = service.getHomeReview(sellerid);
         System.out.println("homeReview : "+result);
         return result;
      }
      
      @GetMapping("homeQnA") 
      @ResponseBody
      public List<QnaDTO> getHomeQna(HttpServletRequest req){
         HttpSession session = req.getSession();
         String sellerid = (String)session.getAttribute("loginSeller");
         
         List<QnaDTO> result = service.getQnaListBySellerid(sellerid);
         System.out.println("홈큐엔에이 : "+result);
         return result;
      }
      
      @GetMapping("mostReviewSBoard") 
      @ResponseBody
      public List<Map<String, Object>> getMostReviewSBoard(HttpServletRequest req){
         HttpSession session = req.getSession();
         String sellerid = (String)session.getAttribute("loginSeller");
         
         List<Map<String, Object>> result = service.getMostReviewSBoard(sellerid);
         return result;
      }
      
      @GetMapping("mostLikeSBoard") 
      @ResponseBody
      public List<Map<String, Object>> getMostLikeSBoard(HttpServletRequest req){
         HttpSession session = req.getSession();
         String sellerid = (String)session.getAttribute("loginSeller");
         
         List<Map<String, Object>> result = service.getMostLikeSBoard(sellerid);
         return result;
      }

      @GetMapping("my/storeList")
      public String SellerMyStoreList(HttpServletRequest req, Model model) {
         HttpSession session = req.getSession();
         String sellerid = (String)session.getAttribute("loginSeller");
         
         List<Map<String, Object>> storeData = new ArrayList<>();
         
         SinfoDTO sInfo = service.getSinfoBySellerid(sellerid);
         System.out.println("컨트롤러 sInfo.getSNum() : "+sInfo.getSNum());
         String storeMainImgSystemname = service.getStoreMainImgBySNum(sInfo.getSNum());
         String url = "/summernoteImage/"+storeMainImgSystemname;
            
         Map<String, Object> map = new HashMap<>();
         map.put("sInfo", sInfo);
         map.put("url", url);
         storeData.add(map);
         String systemname = service.getSellerProfile(sellerid);
 		 model.addAttribute("systemname", systemname);
         model.addAttribute("storeData", storeData);
         System.out.println("컨트롤러 storeData : "+storeData);
         
         return "seller/my/storeList";
      }

      
      @GetMapping("my/storeUpdate")
         public String SellerMyStoreUpdate(String sellerid, HttpServletRequest req, Model model) {
            HttpSession session = req.getSession();
            String loginSeller = (String)session.getAttribute("loginSeller");
            String systemname = service.getSellerProfile(loginSeller);
    		System.out.println(systemname);
    		model.addAttribute("systemname", systemname);
            return "seller/my/storeUpdate";
         }
      
      @GetMapping("checkSName")
      @ResponseBody
      public String checkSName(String sName) {
         if(service.checkSName(sName)) {
            System.out.println("O");
            return "O";
         }
         else { 
            System.out.println("X");
            return "X";
         }
      }
      
      @PostMapping("my/storeInfoUpdate")
         public String SellerMyStoreInfoUpdate(StoreDTO store, HttpServletRequest req) {
            HttpSession session = req.getSession();
            String sellerid = (String)session.getAttribute("loginSeller");
            
            service.updateStore(store, sellerid);
            
            return "seller/my/storeUpdate";
         }
      
      @PostMapping("my/storeContentsUpdate")
         public String SellerMyStoreContentsUpdate(HttpServletRequest req, MultipartFile[] files) throws Exception {
            HttpSession session = req.getSession();
            String sellerid = (String)session.getAttribute("loginSeller");
            
            service.updateStoreBackgroundPicture(files, sellerid);
            
            return "seller/my/storeUpdate";
         }
      
      
     
   //로그인 끝 ====================================================================
         // 현재 재고현황 페이지
         @GetMapping("my/productNow")
         public String SellerMyProductNow(Criteria cri, HttpServletRequest req, Model model) {
            HttpSession session = req.getSession();
	        String sellerid = (String)session.getAttribute("loginSeller");
            List<ProductDTO> productCriList = service.getProductCriList(sellerid,cri);
    		String systemname = service.getSellerProfile(sellerid);
    		model.addAttribute("systemname", systemname);
            model.addAttribute("productList", productCriList);
            model.addAttribute("pageMaker",new PageDTO(service.getTotal(cri, sellerid), cri));
            System.out.println(service.getTotal(cri, sellerid));
            return "seller/my/productNow";
         }
         @GetMapping("my/qna")
         public String SellerMyQnA(HttpServletRequest req, Model model, Criteria cri) {
            cri.setAmount(5);
            System.out.println(cri);
            HttpSession session = req.getSession();
            String sellerid = (String) session.getAttribute("loginSeller");
            String systemname = service.getSellerProfile(sellerid);
            List<QnaDTO> qnaList = new ArrayList<>();
            qnaList = service.getQnaListBySelleridWithCri(cri, sellerid);
            // qnaList = service.getNoAnswerList((String)
            // session.getAttribute("loginSeller"));
            System.out.println(qnaList);
            
            model.addAttribute("systemname", systemname);
            model.addAttribute("pageMaker", new PageDTO(service.getQnaTotalBySellerid(sellerid), cri));
            model.addAttribute("qnaList", qnaList);
            model.addAttribute("cri", cri);
            return "seller/my/qna";
         }

         @GetMapping("my/qnaNoAnswer")
         public String SellerMyQnANoAnswer(HttpServletRequest req, Model model, Criteria cri) {
            cri.setAmount(5);
            System.out.println(cri);
            HttpSession session = req.getSession();
            String sellerid = (String) session.getAttribute("loginSeller");
            List<QnaDTO> qnaList = new ArrayList<>();
            System.out.println(qnaList);
            qnaList = service.getNoAnswerQnaListBySelleridWithCri(cri, sellerid);
            // qnaList =
            // service.getQnaListBySellerid((String)session.getAttribute("loginSeller"));

            System.out.println(qnaList);
            model.addAttribute("pageMaker", new PageDTO(service.getNoAnswerQnaTotalBySellerid(sellerid), cri));
            model.addAttribute("qnaList", qnaList);
            model.addAttribute("cri", cri);
            return "seller/my/noAnswerQnA";
         }

         @GetMapping("my/reviewList")
               public String SellerMyReviewList(HttpServletRequest req, Model model, Criteria cri) {
                  System.out.println(cri);
                  cri.setAmount(5);
                  HttpSession session = req.getSession();
                  String sellerid = (String) session.getAttribute("loginSeller");
                  char type = 'M';

                  List<SBoardDTO> sBoardList = service.getSBoardListBySellerid(sellerid);
                  List<ProductDTO> productList = service.getProductListBySelleridType(sellerid, type);
                  List<ReviewDTO> reviewList = service.getReviewListBySelleridWithCri(cri, sellerid);
                  List<FileDTO> sBoardThumbnailList = new ArrayList<>();
                  
                  for(SBoardDTO sBoard : sBoardList) {
                     int sBoardNum = 0;
                     sBoardNum = sBoard.getSBoardnum();
                     
                     FileDTO Thumbnail = service.getSBoardThumbnailBySNum(sBoardNum);
                     String tn = "/summernoteImage/"+Thumbnail.getSystemname();
                     Thumbnail.setSystemname(tn);
                
                     sBoardThumbnailList.add(Thumbnail);
                  }
                  System.out.println(reviewList);
                  String systemname = service.getSellerProfile(sellerid);
                  System.out.println("컨트롤러 sBoardList : " + sBoardList);
                  System.out.println("컨트롤러 sBoard썸네일 : " + sBoardThumbnailList);
                  model.addAttribute("systemname", systemname);
                  model.addAttribute("sBoardList", sBoardList);
                  model.addAttribute("productList", productList);
                  model.addAttribute("reviewList", reviewList);
                  model.addAttribute("pageMaker", new PageDTO(service.getReviewTotalBySellerid(sellerid), cri));
                  model.addAttribute("sBoardThumbnailList", sBoardThumbnailList);
                  model.addAttribute("cri", cri);

                  return "seller/my/reviewList";
               }

         @PostMapping("my/productSearch")
         @ResponseBody
         public List<ProductDTO> productSearch(@RequestBody Map<String, String> request, Criteria cri, HttpServletRequest req) {
             String text = request.get("text");
             HttpSession session = req.getSession();
             String sellerid = (String) session.getAttribute("loginSeller");
             
             List<ProductDTO> pList = service.getCriSearchProduct(text, sellerid, cri);
             System.out.println("검색결과: "+pList);
             return pList;
         }
         @GetMapping("my/myInfoCheck")
         public String SellerMyInfo(String sellerid, HttpServletRequest req, Model model) {
            HttpSession session = req.getSession();
            String loginSeller = (String) session.getAttribute("loginSeller");
            String systemname = service.getSellerProfile(loginSeller);
            model.addAttribute("systemname", systemname);
            return "seller/my/myInfoCheck";
         }

      @PostMapping("my/myInfo")
         public String SellerInfoCheck(SellerDTO seller, HttpServletRequest req, Model model) {
            HttpSession session = req.getSession();
            String loginSeller = (String) session.getAttribute("loginSeller");
            String systemname = service.getSellerProfile(loginSeller);
            String sellerid = seller.getSellerid();
            String sellerpw = seller.getSellerpw();
            System.out.println("아이디: "+sellerid);
            if(service.checkId(sellerid) != null) {
            	if(service.checkId(sellerid).equals(loginSeller)) {
            		if(service.checkPw(sellerpw) != null) {
            			model.addAttribute("systemname", systemname);
            			return "seller/my/myInfo";
            		}
            		else {
            			model.addAttribute("systemname", systemname);
            			return "seller/my/myInfoCheck";
            		}
            	}
            	else {
            		model.addAttribute("systemname", systemname);
            		return "seller/my/myInfoCheck";
            	}
            	
            }
            return "seller/my/home";
        }


      @PostMapping("my/mySellerInfoUpdate")
         public String SellerInfoUpdate(SellerDTO seller,HttpServletRequest req) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%"+seller);
            HttpSession session = req.getSession();
            String loginSeller = (String) session.getAttribute("loginSeller");
            
            if(seller == null) {
            
            }
            else {
               service.updateSeller(seller, loginSeller);
            }
            return "seller/my/myInfo";
            
         }


      @PostMapping("my/mySellerProfile")
         public String SellerUpdateProfile(HttpServletRequest req, MultipartFile[] files) throws Exception {
            HttpSession session = req.getSession();
            String loginSeller = (String) session.getAttribute("loginSeller");
            
            if(files == null) {
               return "seller/my/myInfo";
            }
            else {
               service.updateSellerProfile(files, loginSeller);
            }
            return "seller/my/myInfo";
         
         }


      @GetMapping("my/deleteSeller")
         public String DeleteSeller(HttpServletRequest req) {
            HttpSession session = req.getSession();
            String sellerid = (String) session.getAttribute("loginSeller");
            
            if(service.deleteSeller(sellerid)) {
               System.out.println("seller 전체 삭제 성공");
               return "/user/login";
            }
            else {
               System.out.println("seller 전체 삭제 실패");
               return "/seller/my/myInfo";
            }
            
         }

}
