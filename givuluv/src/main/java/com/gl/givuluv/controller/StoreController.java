package com.gl.givuluv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.QnaDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SBoardwithFileDTO;
import com.gl.givuluv.domain.dto.StoreDTO;
import com.gl.givuluv.service.FileService;
import com.gl.givuluv.service.ProductService;
import com.gl.givuluv.service.QnaService;
import com.gl.givuluv.service.SBoardService;
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
	
	
	
	@GetMapping("sBoard")
	public String replace(Model model, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		String loginUser = (String)session.getAttribute("loginUser");
		String loginSeller = (String)session.getAttribute("loginSeller");
		
		List<ProductDTO> plists = pservice.getList();
		
		List<SBoardwithFileDTO> sBoardList = new ArrayList<>();
		
		
		for (ProductDTO product : plists) {
	        
	        // 물품번호로 물품관련(스토어, 이미지, 정보들 가져오기)
	        
	        int connectid = Integer.parseInt(product.getConnectid());
	        
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
		
		model.addAttribute("sBoardList", sBoardList);
		model.addAttribute("loginUser", loginUser);
		model.addAttribute("loginSeller", loginSeller);
		
		
		return "store/sBoard";
	}
	
	@GetMapping("getCategoryItems")
	public @ResponseBody List<SBoardwithFileDTO> getCategoryItems(@RequestParam String category) {
		List<SBoardwithFileDTO> resultList = new ArrayList<>();
		
		
		if(category.equals("전체")) {
			List<ProductDTO> alllists = pservice.getList();
			for(ProductDTO product : alllists) {
				
				// 물품번호로 물품관련(스토어, 이미지, 정보들 가져오기)
		        
		        int connectid = Integer.parseInt(product.getConnectid());
		        
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
		                    String p_category = sellservice.getCategory(snum);
		                    
		                    SBoardwithFileDTO sboarddto = new SBoardwithFileDTO();
		                    
		                    sboarddto.setProduct(product);
		                    sboarddto.setProductFile(productFile);
		                    sboarddto.setSBoard(sboard);
		                    // sboarddto.setStoreFile(storeFile);
		                    sboarddto.setStorename(storeName);
		                    sboarddto.setCategory(p_category);
		                    
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
		}
		else {
			List<ProductDTO> plists = pservice.getCategoryList(category);
			for(ProductDTO product : plists) {
				
				// 물품번호로 물품관련(스토어, 이미지, 정보들 가져오기)
		        
		        int connectid = Integer.parseInt(product.getConnectid());
		        
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
		                    String p_category = sellservice.getCategory(snum);
		                    
		                    SBoardwithFileDTO sboarddto = new SBoardwithFileDTO();
		                    
		                    sboarddto.setProduct(product);
		                    sboarddto.setProductFile(productFile);
		                    sboarddto.setSBoard(sboard);
		                    // sboarddto.setStoreFile(storeFile);
		                    sboarddto.setStorename(storeName);
		                    sboarddto.setCategory(p_category);
		                    
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
		}
		
		return resultList;
	}
	
	
	@GetMapping("write")
	public void write() {
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
          }

       }
       System.out.println("실패");
       return "store/sBoard";
    }
	   
	@GetMapping("productView")
	public String getProduct(@RequestParam int productnum, Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String loginUser = (String)session.getAttribute("loginUser");
		
		ProductDTO product = pservice.getProduct(productnum);
		
		int connectid = Integer.parseInt(product.getConnectid());
		
		//물품 이미지파일 가져오기
		FileDTO imgFile = fservice.getSBoardFile(connectid);
		
		//커넥트 아이디로 s_board 가져오기
		SBoardDTO sboard = sBservice.getSBoard(connectid);
		
		
		
		StoreDTO store = sservice.getStoreList(sboard.getSNum());
		int cost = product.getCost();
		
		//리뷰량 Q&A 가져오기
		List<QnaDTO> qnaList = qservice.getQnaList(productnum);
		
		model.addAttribute("product", product);
		model.addAttribute("imgFile", imgFile);
		model.addAttribute("sboard", sboard);
		model.addAttribute("store", store);
		model.addAttribute("cost", cost);
		model.addAttribute("loginUser", loginUser);
		
		
		model.addAttribute("qnaList", qnaList);
		
		
		
		
		
		return "store/productView";
		
	}
}










