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
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SBoardwithFileDTO;
import com.gl.givuluv.domain.dto.StoreDTO;
import com.gl.givuluv.service.FileService;
import com.gl.givuluv.service.ProductService;
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
	private FileService fservice;
	@Autowired
	private SellerService sellservice;
	
	
	
	@GetMapping("sBoard")
	public String replace(Model model) {
		
		List<ProductDTO> plists = pservice.getList();
		
		List<SBoardwithFileDTO> sBoardList = new ArrayList<>();
		
		
		for(ProductDTO product : plists) {
			
			//물품번호로 물품관련(스토어, 이미지, 정보들 가져오기)
			
			//물품 이미지파일 가져오기
			int productnum = Integer.parseInt(product.getProductnum());
			FileDTO ProductFile = fservice.getFileByProductnum(productnum);
			
			//물품등록한 스토어정보 가져오기
			int connectid = Integer.parseInt(product.getConnectid());
			StoreDTO store = sservice.getStoreList(connectid);
			if(store != null) {
				//스토어 이미지파일 가져오기
				int storenum = store.getSNum();
				FileDTO StoreFile = fservice.getFileByStorenum(storenum);
				
				//스토어(셀러) 카테고리 가져오기
				String sellerid = store.getSellerid();
				String categoty = sellservice.getCategory(sellerid);
				
				
				SBoardwithFileDTO sboarddto = new SBoardwithFileDTO();
				
				sboarddto.setProduct(product);
				sboarddto.setProductFile(ProductFile);
				sboarddto.setStore(store);
				sboarddto.setStoreFile(StoreFile);
				sboarddto.setCategory(categoty);
				
				sBoardList.add(sboarddto);
			}
			else {
				System.out.println("물품은 있지만 스토어가 없음");
			}
			
		}
		
		model.addAttribute("sBoardList", sBoardList);
		
		
		return "store/sBoard";
	}
	
	@GetMapping("getCategoryItems")
	public @ResponseBody List<SBoardwithFileDTO> getCategoryItems(@RequestParam String category) {
		List<SBoardwithFileDTO> resultList = new ArrayList<>();
		
		
		if(category.equals("전체")) {
			List<ProductDTO> alllists = pservice.getList();
			for(ProductDTO product : alllists) {
				
				//물품번호로 물품관련(스토어, 이미지, 정보들 가져오기)
				
				//물품 이미지파일 가져오기
				int productnum = Integer.parseInt(product.getProductnum());
				FileDTO ProductFile = fservice.getFileByProductnum(productnum);
				
				//물품등록한 스토어정보 가져오기
				int connectid = Integer.parseInt(product.getConnectid());
				StoreDTO store = sservice.getStoreList(connectid);
				
				if(store != null) {
					//스토어 이미지파일 가져오기
					int storenum = store.getSNum();
					FileDTO StoreFile = fservice.getFileByStorenum(storenum);
					
					//스토어(셀러) 카테고리 가져오기
					String sellerid = store.getSellerid();
					String categoty = sellservice.getCategory(sellerid);
					
					SBoardwithFileDTO sboarddto = new SBoardwithFileDTO();
					
					sboarddto.setProduct(product);
					sboarddto.setProductFile(ProductFile);
					sboarddto.setStore(store);
					sboarddto.setStoreFile(StoreFile);
					sboarddto.setCategory(categoty);
					
					resultList.add(sboarddto);
				}
				else {
					System.out.println("물품은 있지만 스토어가 없음");
				}
				
			}
		}
		else {
			List<ProductDTO> plists = pservice.getCategoryList(category);
			for(ProductDTO product : plists) {
				
				//물품번호로 물품관련(스토어, 이미지, 정보들 가져오기)
				
				//물품 이미지파일 가져오기
				int productnum = Integer.parseInt(product.getProductnum());
				FileDTO ProductFile = fservice.getFileByProductnum(productnum);
				
				//물품등록한 스토어정보 가져오기
				int connectid = Integer.parseInt(product.getConnectid());
				StoreDTO store = sservice.getStoreList(connectid);
				
				if(store != null) {
					//스토어 이미지파일 가져오기
					int storenum = store.getSNum();
					FileDTO StoreFile = fservice.getFileByStorenum(storenum);
					
					//스토어(셀러) 카테고리 가져오기
					String sellerid = store.getSellerid();
					String categoty = sellservice.getCategory(sellerid);
					
					SBoardwithFileDTO sboarddto = new SBoardwithFileDTO();
					
					sboarddto.setProduct(product);
					sboarddto.setProductFile(ProductFile);
					sboarddto.setStore(store);
					sboarddto.setStoreFile(StoreFile);
					sboarddto.setCategory(categoty);
					
					resultList.add(sboarddto);
				}
				else {
					System.out.println("물품은 있지만 스토어가 없음");
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
		
		ProductDTO pdto = pservice.getProduct(productnum);
		FileDTO fdto = fservice.getFileByProductnum(productnum);
		
		StoreDTO sdto = sservice.getStoreList(Integer.parseInt(pdto.getConnectid()));
		int cost = Integer.parseInt(pdto.getCost());
		
		
		model.addAttribute("pdto", pdto);
		model.addAttribute("fdao", fdto);
		model.addAttribute("sdto", sdto);
		model.addAttribute("cost", cost);
		model.addAttribute("loginUser", loginUser);
		
		
		//리뷰량 Q&A 가져오기
		
		
		return "store/productView";
		
	}
}










