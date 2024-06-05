package com.gl.givuluv.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.LikeDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.QnaDTO;
import com.gl.givuluv.domain.dto.ReviewDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SBoardwithFileDTO;
import com.gl.givuluv.domain.dto.SPaymentDTO;
import com.gl.givuluv.domain.dto.SRegisterDTO;
import com.gl.givuluv.domain.dto.StoreDTO;
import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.service.FileService;
import com.gl.givuluv.service.ProductService;
import com.gl.givuluv.service.QnaService;
import com.gl.givuluv.service.ReviewService;
import com.gl.givuluv.service.SBoardService;
import com.gl.givuluv.service.SPaymentService;
import com.gl.givuluv.service.SellerService;
import com.gl.givuluv.service.StoreService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/store/*")
public class StoreController {
	
	@Autowired
	private ProductService pservice;
	@Autowired
	private StoreService sservice;
	@Autowired
	private SBoardService sBservice;
	@Autowired
	private FileService fservice;
	@Autowired
	private SellerService sellservice;
	@Autowired
	private QnaService qservice;
	@Autowired
	private ReviewService rservice;
	@Autowired
	private SPaymentService spservice;
	
	
	
	@GetMapping("sBoard")
	public String replace(Model model, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		String loginUser = (String)session.getAttribute("loginUser");
		String loginSeller = (String)session.getAttribute("loginSeller");
		String loginOrg = (String)session.getAttribute("loginOrg");
		String loginManager = (String)session.getAttribute("loginManager");
		
		//중복되는 connectid 제외하고 가져오기
		int[] cid = pservice.getMConnectid();
		List<SBoardwithFileDTO> sBoardList = new ArrayList<>();
		
		//가져온 connectid로 정보들 가져오기
		for(int connectid : cid) {
			ProductDTO product = pservice.getSList(connectid);
			
			// 물품번호로 물품관련(스토어, 이미지, 정보들 가져오기)
			// 물품 이미지파일 가져오기
			FileDTO productFile = fservice.getSBoardFile(connectid);
			
			//로그인유저 좋아요 가져오기
			LikeDTO like = sservice.getSBoardLike(connectid, loginUser);
			
			if (productFile != null) {
				// 물품등록한 스토어정보 가져오기
				// 커넥트 아이디로 sboard 가져오기
				SBoardDTO sboard = sBservice.getSBoard(connectid);
				
				if (sboard != null) {
					int snum = sboard.getSNum();
					
					// 스토어 정보 가져오기
					StoreDTO store = sservice.getStoreList(snum);
					
					if (store != null) {
						// 스토어 로고파일 가져오기(스토어 등록할때 이미지 넣으면 구현)
						// FileDTO storeFile = fservice.getFileByStorenum(store.getSNum());
						
						// 스토어 이름 가져오기
						String storeName = store.getSName();
						
						// 카테고리 가져오기
						String category = sellservice.getCategory(snum);
						
						SBoardwithFileDTO sboarddto = new SBoardwithFileDTO();
						
						sboarddto.setProduct(product);
						sboarddto.setProductFile(productFile);
						sboarddto.setSBoard(sboard);
						// sboarddto.setStoreFile(storeFile);
						sboarddto.setStorename(storeName);
						sboarddto.setCategory(category);
						sboarddto.setLike(like);
						
						
						sBoardList.add(sboarddto);
					} else {
						System.out.println("스토어 정보를 가져올 수 없음");
					}
				} else {
					System.out.println("물품은 있지만 sBoard가 없음");
				}
			} else {
				System.out.println("파일 정보를 가져올 수 없음");
			}
		}
		
		model.addAttribute("sBoardList", sBoardList);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("loginSeller", loginSeller);
		model.addAttribute("loginOrg", loginOrg);
		model.addAttribute("loginManager", loginManager);
		
		return "store/sBoard";
	}
	
	@GetMapping("getCategoryItems")
	public @ResponseBody List<SBoardwithFileDTO> getCategoryItems(@RequestParam String category, HttpServletRequest req) {
		List<SBoardwithFileDTO> resultList = new ArrayList<>();
		int[] cid;
		
		HttpSession session = req.getSession();
		String loginUser = (String)session.getAttribute("loginUser");
		
		if(category.equals("전체")) {
			//중복되는 connectid 제외하고 가져오기
			cid = pservice.getMConnectid();
		}
		else {
			cid = pservice.getMConnectidByCategory(category);
		}
		//가져온 connectid로 정보들 가져오기
		for(int connectid : cid) {
			ProductDTO product = pservice.getSList(connectid);
			
			// 물품번호로 물품관련(스토어, 이미지, 정보들 가져오기)
			// 물품 이미지파일 가져오기
			FileDTO productFile = fservice.getSBoardFile(connectid);
			
			//로그인유저 좋아요 가져오기
			LikeDTO like = sservice.getSBoardLike(connectid, loginUser);
			
			if (productFile != null) {
				// 물품등록한 스토어정보 가져오기
				// 커넥트 아이디로 sboard 가져오기
				SBoardDTO sboard = sBservice.getSBoard(connectid);
				
				if (sboard != null) {
					int snum = sboard.getSNum();
					
					// 스토어 정보 가져오기
					StoreDTO store = sservice.getStoreList(snum);
					
					if (store != null) {
						// 스토어 로고파일 가져오기(스토어 등록할때 이미지 넣으면 구현)
						// FileDTO storeFile = fservice.getFileByStorenum(store.getSNum());
						
						// 스토어 이름 가져오기
						String storeName = store.getSName();
						
						// 카테고리 가져오기
						String p_category = sellservice.getCategory(snum);
						
						SBoardwithFileDTO sboarddto = new SBoardwithFileDTO();
						
						sboarddto.setProduct(product);
						sboarddto.setProductFile(productFile);
						sboarddto.setSBoard(sboard);
						// sboarddto.setStoreFile(storeFile);
						sboarddto.setStorename(storeName);
						sboarddto.setCategory(p_category);
						sboarddto.setLike(like);
						
						resultList.add(sboarddto);
					} else {
						System.out.println("스토어 정보를 가져올 수 없음");
					}
				} else {
					System.out.println("물품은 있지만 sBoard가 없음");
				}
			} else {
				System.out.println("파일 정보를 가져올 수 없음");
			}
		}
		return resultList;
	}
	
	
	// MDM
	@GetMapping("write")
	public String write(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String loginSeller = (String) session.getAttribute("loginSeller");

		char check = sservice.checkStoreBySellerid(loginSeller);

		String url;

		if (check == 'o') {
			if (sellservice.storeInfoCheck(loginSeller)) {
				// 승인을 받은 후 스토어 등록을 했다면
				return "store/write";
			} else {
				// 승인을 받은 후 스토어 등록을 안했다면
				url = "storewrite";
				model.addAttribute("alertMessage", "스토어 등록 후 이용가능합니다.");
				model.addAttribute("redirectUri", url);
				return "store/storeMassege";
			}
		} else if (check == '-') {
			url = "/";
			model.addAttribute("alertMessage", "스토어 승인 대기 중입니다.\n승인 후 스토어 및 가게 등록이 가능합니다.");
			model.addAttribute("redirectUri", url);
			return "store/storeMassege";
		} else {
			url = "storeSignup";
			model.addAttribute("alertMessage", "스토어 승인 후 스토어 및 가게 등록이 가능합니다.");
			model.addAttribute("redirectUri", url);
			return "store/storeMassege";
		}
	}

	@PostMapping("write")
    public String regist(Model model, SBoardDTO sBoard, String sellerId,
          @ModelAttribute("productList") ProductDTO productList, String filenames, MultipartFile thumbnail,
          HttpServletRequest req) throws Exception {
       // 경로
       System.out.println("Post : store/write");
       // 파라미터 출력
       System.out.println(sBoard.getSTitle());
       System.out.println(sBoard.getSContent());
       System.out.println(sellerId);
       List<ProductDTO> products = productList.getProductList();
       for (ProductDTO product : products) {
          System.out.println(product.getProductname());
          System.out.println(product.getCost());
          System.out.println(product.getPAmount());
       }
       System.out.println(filenames);
       // 세션 검사
       HttpSession session = req.getSession();
       String sessionSellerId = session.getAttribute("loginSeller").toString();
       if (sessionSellerId.equals(sellerId)) {
			if (sservice.regist(model, sBoard, sellerId, products, filenames, thumbnail)) {
				System.out.println("성공");

				int[] cid = pservice.getMConnectid();
				List<SBoardwithFileDTO> sBoardList = new ArrayList<>();

				// 가져온 connectid로 정보들 가져오기
				for (int connectid : cid) {
					ProductDTO product = pservice.getSList(connectid);

					// 물품번호로 물품관련(스토어, 이미지, 정보들 가져오기)
					System.out.println(connectid);
					// 물품 이미지파일 가져오기
					FileDTO productFile = fservice.getSBoardFile(connectid);

					if (productFile != null) {
						// 물품등록한 스토어정보 가져오기
						// 커넥트 아이디로 sboard 가져오기
						SBoardDTO sboard = sBservice.getSBoard(connectid);

						if (sboard != null) {
							int snum = sboard.getSNum();

							// 스토어 정보 가져오기
							StoreDTO store = sservice.getStoreList(snum);

							if (store != null) {
								// 스토어 로고파일 가져오기(스토어 등록할때 이미지 넣으면 구현)
								// FileDTO storeFile = fservice.getFileByStorenum(store.getSNum());

								// 스토어 이름 가져오기
								String storeName = store.getSName();

								// 카테고리 가져오기
								String category = sellservice.getCategory(snum);

								SBoardwithFileDTO sboarddto = new SBoardwithFileDTO();

								sboarddto.setProduct(product);
								sboarddto.setProductFile(productFile);
								sboarddto.setSBoard(sboard);
								// sboarddto.setStoreFile(storeFile);
								sboarddto.setStorename(storeName);
								sboarddto.setCategory(category);

								sBoardList.add(sboarddto);
							} else {
								System.out.println("스토어 정보를 가져올 수 없음");
							}
						} else {
							System.out.println("물품은 있지만 sBoard가 없음");
						}
					} else {
						System.out.println("파일 정보를 가져올 수 없음");
					}
				}

				if (sBoardList != null) {
					model.addAttribute("sBoardList", sBoardList);
					return "store/sBoard";
				}
          }
       }
       else {
    	   System.out.println("실패");
       }
       return "store/sBoard";
    }
	
	
	//MDM
	@GetMapping("storewrite")
	public String storewrite(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String loginSeller = (String)session.getAttribute("loginSeller");
		
		char check = sservice.checkStoreBySellerid(loginSeller);
		
		String url;
		
		if(check == 'o') {
        	if(sellservice.storeInfoCheck(loginSeller)) {
        		//승인을 받은 후 스토어 등록을 했다면 메이페이지 이동하기
        		url = "seller/my/home";
      			model.addAttribute("alertMessage", "이미 등록한 스토어가 있습니다.");
      			model.addAttribute("redirectUri", url);
      			return "store/storeMassege";
        	}
        	else {
        		//승인을 받은 후 스토어 등록을 안했다면
        		return "store/storewrite";
        	}
		}
		else if(check == '-'){
			url = "/";
			model.addAttribute("alertMessage", "스토어 승인 대기 중입니다.\n승인 후 스토어 및 가게 등록이 가능합니다.");
			model.addAttribute("redirectUri", url);
			return "store/storeMassege";
		}
		else {
			url = "storeSignup";
			model.addAttribute("alertMessage", "스토어 승인 신청 후 이용가능합니다..");
			model.addAttribute("redirectUri", url);
			return "store/storeMassege";
		}
	}

	
	
	
	@GetMapping("productView")
	public String getProduct(@RequestParam int productnum, Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String loginUser = (String)session.getAttribute("loginUser");
		String loginSeller = (String)session.getAttribute("loginSeller");
		String loginOrg = (String)session.getAttribute("loginOrg");
		String loginManager = (String)session.getAttribute("loginManager");
		
		//등록된 물품 목록 가져오기
		List<ProductDTO> products = pservice.getProduct(productnum);
		
		//등록된 물품의 0번 방에 있는 커넥트 아이디 가져오기
		int connectid = Integer.parseInt(products.get(0).getConnectid());
		
		//물품 이미지파일 가져오기
		FileDTO imgFile = fservice.getSBoardFile(connectid);
		
		//커넥트 아이디로 s_board 가져오기
		SBoardDTO sboard = sBservice.getSBoard(connectid);
		
		//스토어 가져오기
		StoreDTO store = sservice.getStoreList(sboard.getSNum());
		
		//리뷰량 Q&A 가져오기
		List<QnaDTO> qnaList = qservice.getQnaList(productnum);
		List<ReviewDTO> reviewList = rservice.getProductReviewList(sboard.getSBoardnum());
		
		model.addAttribute("products", products);
		model.addAttribute("imgFile", imgFile);
		model.addAttribute("sboard", sboard);
		model.addAttribute("store", store);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("loginSeller", loginSeller);
		model.addAttribute("loginOrg", loginOrg);
		model.addAttribute("loginManager", loginManager);
		
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("reviewList", reviewList);
		
		return "store/productView";
	}
		@GetMapping("productPay")
		public String productPay(
		        @RequestParam("productnum") int productnum, 
		        @RequestParam("cost") int cost, 
		        @RequestParam("productCnt") int productCnt, 
		        @RequestParam("productname") String productname, 
		        @RequestParam("storename") String storename, 
		        HttpServletRequest req, Model model) {
		    
		    HttpSession session = req.getSession();
		    String userid = (String)session.getAttribute("loginUser");
		    UserDTO user = sBservice.getUserById(userid);
		    SBoardDTO sboard = sBservice.getSboardByProductnum(productnum);
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
		    
		    getBonus = (int) ((cost + orderCost) * 0.1);
		     
		    model.addAttribute("sboard", sboard);
		    model.addAttribute("sBoardnum", sboard.getSBoardnum());
		    model.addAttribute("cost", cost);
		    model.addAttribute("orderCost", orderCost);
		    model.addAttribute("getBonus", getBonus);
		    model.addAttribute("productname", productname);
		    model.addAttribute("productCnt", productCnt);
		    model.addAttribute("storename", storename);
		    model.addAttribute("user", user);
		    model.addAttribute("productnum", productnum);
		    
		    return "store/productPay";
		}
		
		@GetMapping("sReceipt")
		public String fReceipt(int paymentnum, Model model) {
			SPaymentDTO spayment = spservice.getSPaymentByPaymentnum(paymentnum);
			UserDTO user = spservice.getUserBySPaymentnum(spayment.getPaymentnum());
			ProductDTO product = spservice.getSProductByProductnum(spayment.getProductnum());
			SBoardDTO sboard = spservice.getSBoardBySBoardnum(spayment.getSBoardnum());
			System.out.println(spayment);
			System.out.println(sboard);
			System.out.println(product);
			System.out.println(user);
			
			model.addAttribute("spayment", spayment);
			model.addAttribute("product", product);
			model.addAttribute("user", user);
			model.addAttribute("sboard", sboard);
			return "store/sReceipt";
		}
		
		
		//MDM
		@GetMapping("likeChoice")
		@ResponseBody
		public String likeChoice(@RequestParam int sBoardnum, HttpServletRequest req) {
		    HttpSession session = req.getSession();
		    String userid = (String)session.getAttribute("loginUser");
		    
		    LikeDTO likedto = new LikeDTO();
		    likedto.setConnectid(sBoardnum);
		    likedto.setUserid(userid);
		    likedto.setType('s');
		    
		    if (sservice.insertLikeSBoard(likedto)) {
		        System.out.println("O");
		        return "O";
		    } else {
		        System.out.println("X");
		        return "X";
		    }
		}
		
		
		//MDM
		@GetMapping("likeDelete")
		@ResponseBody
		public String likeDelete(@RequestParam int sBoardnum, HttpServletRequest req) {
			HttpSession session = req.getSession();
			String userid = (String)session.getAttribute("loginUser");
			
			
			if(sservice.deleteLikeSBoard(sBoardnum, userid)) {
				System.out.println("O");
				return "O";
			}
			else {
				System.out.println("X");
				return "X";
			}
		}
		
		@GetMapping("storeSignup")
		public String storeSignup(HttpServletRequest req, Model model) {
			
			HttpSession session = req.getSession();
			String loginSeller = (String)session.getAttribute("loginSeller");
			
			char check = sservice.checkStoreBySellerid(loginSeller);
			
			String url;
			
			if(check == 'o') {
				url = "/";
				model.addAttribute("alertMessage", "이미 승인된 스토어입니다.");
				model.addAttribute("redirectUri", url);
				return "store/storeMassege";
			}
			else if(check == '-'){
				url = "/";
				model.addAttribute("alertMessage", "스토어 승인 대기 중입니다.");
				model.addAttribute("redirectUri", url);
				return "store/storeMassege";
			}
			else {
				return "store/storeSignup";
			}
		}
		
	    @GetMapping("checkStorename")
		@ResponseBody
	    public String checkStorename(@RequestParam String storename) {
	    	if(sservice.checkStorename(storename)) {
				System.out.println("O");
				return "O";
			}
			else {
				System.out.println("X");
				return "X";
			}
	    }
	    
	    @GetMapping("checkRegnum")
	    @ResponseBody
	    public String checkRegnum(@RequestParam String regnum) {
	    	if(sservice.checkRegnum(regnum)) {
	    		System.out.println("O");
	    		return "O";
	    	}
	    	else {
	    		System.out.println("X");
	    		return "X";
	    	}
	    }
	    
	    
	    @GetMapping("signup")
	    @ResponseBody
	    public String signup(
	    		@RequestParam String storename, 
	    		@RequestParam String regnum,
	    		@RequestParam String leadername, 
	    		@RequestParam String storePhone,
	    		@RequestParam String s_zipcode, 
	    		@RequestParam String s_addr,
	    		@RequestParam String s_addrdetail, 
	    		@RequestParam String s_addretc,
	    		@RequestParam String information,
	            HttpServletRequest req) {

	        HttpSession session = req.getSession();
	        String loginSeller = (String) session.getAttribute("loginSeller");

	        SRegisterDTO srdto = new SRegisterDTO();

	        srdto.setSName(storename);
	        srdto.setSRegnum(regnum);
	        srdto.setSLeader(leadername);
	        srdto.setSPhone(storePhone);
	        srdto.setSZipcode(s_zipcode);
	        srdto.setSAddr(s_addr);
	        srdto.setSAddrdetail(s_addrdetail);
	        srdto.setSAddretc(s_addretc);
	        srdto.setSellerid(loginSeller);
	        srdto.setInformation(information);

	        System.out.println(srdto);
	        
	        if (sservice.insertStoreSignup(srdto)) {
	        	return "O";
	        } else {
	        	return "X";
	        }
	    }
	    
	    // 나중에 링크 만들면 넣어주기
	    @GetMapping("storeview")
	    public String getMethodName(@RequestParam int storenum, HttpServletRequest req, Model model) {
	    	HttpSession session = req.getSession();
	    	
			String loginUser = (String)session.getAttribute("loginUser");
			String loginSeller = (String)session.getAttribute("loginSeller");
			String loginOrg = (String)session.getAttribute("loginOrg");
			String loginManager = (String)session.getAttribute("loginManager");
			
	    	List<SBoardwithFileDTO> sBoardList = sservice.getStoreViewProduct(storenum, loginUser);
	    	
	    	model.addAttribute("sBoardList", sBoardList);
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("loginSeller", loginSeller);
			model.addAttribute("loginOrg", loginOrg);
			model.addAttribute("loginManager", loginManager);
			
			
	    	return "store/storeview";
	    }
	    
	}










