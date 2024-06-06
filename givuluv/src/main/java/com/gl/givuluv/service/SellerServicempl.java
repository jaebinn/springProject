package com.gl.givuluv.service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.catalina.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.Criteria;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.LikeDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.QnaDTO;
import com.gl.givuluv.domain.dto.ReviewDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SPaymentDTO;
import com.gl.givuluv.domain.dto.SellerDTO;
import com.gl.givuluv.domain.dto.StoreDTO;
import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.mapper.BoardMapper;
import com.gl.givuluv.mapper.FileMapper;
import com.gl.givuluv.mapper.LikeMapper;
import com.gl.givuluv.mapper.PaymentMapper;
import com.gl.givuluv.mapper.ProductMapper;
import com.gl.givuluv.mapper.QnaMapper;
import com.gl.givuluv.mapper.ReviewMapper;
import com.gl.givuluv.mapper.SRegisterMapper;
import com.gl.givuluv.mapper.SellerMapper;
import com.gl.givuluv.mapper.StoreMapper;

@Service
public class SellerServicempl implements SellerService{
   
   @Value("${file.dir}")
   private String saveFolder;
   
   @Autowired
   private SellerMapper sellmapper;
   @Autowired
   private StoreMapper storeMapper;
   @Autowired
   private BoardMapper boardMapper;
   @Autowired
   private ProductMapper productMapper;
   @Autowired
   private QnaMapper qnaMapper;
   @Autowired
   private ReviewMapper reviewMapper;
   @Autowired
   private FileMapper fmapper;
   @Autowired
   private LikeMapper likeMapper;
   @Autowired
   private PaymentMapper paymentMapper;
   @Autowired
   private SRegisterMapper srMapper;
   
   @Override
   public boolean join(SellerDTO seller, MultipartFile[] files) throws Exception{
      if(sellmapper.insertSeller(seller) != 1) {
    	  return false;
      }
      if(files == null || files.length == 0) {
			return true;
		}
		else {
			//방금 등록한 게시글 번호
			boolean flag = false;
			System.out.println("파일 개수 : "+files.length);
			
			for(int i=0;i<files.length;i++) {
				MultipartFile file = files[i];
				System.out.println(file.getOriginalFilename());
				
				String sellername = file.getOriginalFilename();
				//5
				int lastIdx = sellername.lastIndexOf(".");
				//.png
				String extension = sellername.substring(lastIdx);
				
				LocalDateTime now = LocalDateTime.now();
				String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

				String systemname = time+UUID.randomUUID().toString()+extension;
				
				String path = saveFolder+systemname;
				
				//File DB 저장
				FileDTO fdto = new FileDTO();
				fdto.setSystemname(systemname);
				fdto.setConnectionid(seller.getSellerid());
				fdto.setType('P');
				System.out.println("fdto 잘 포장함."+fdto);
				
				flag = fmapper.insertFile(fdto) == 1;
				System.out.println("DB에 fdto 잘 넣음.");
				
				//실제 파일 업로드
				file.transferTo(new File(path));
				System.out.println("실제 파일을 업로드 잘함.");
				
				if(!flag) {
					//업로드했던 파일 삭제, 게시글 데이터 삭제, 파일 data 삭제, ...
					System.out.println("flag false:업로드 실패");
					return false;
				}
			}
			System.out.println("flag ture:업로드 성공");
			return true;
		}
   }

   @Override
   public boolean checkIdAndEmail(String sellerid, String email) {
      SellerDTO seller = sellmapper.getSellerById_duplication(sellerid);
      if(sellerid != null) {
         if(seller.getEmail().equals(email)) {
            return true;
         }
      }
      return false;
   }

   @Override
   public boolean checkId_duplication(String sellerid) {
      
      return sellmapper.getSellerById_duplication(sellerid) == null;
   }
   
//   boolean 타입이 아님 pw를 받아와야 함
   @Override
   public String checkPw(String sellerpw) {
      
      return sellmapper.getSellerByPw(sellerpw) ;
   }

   
   @Override
   public boolean login(String sellerid, String sellerpw) {
      SellerDTO user = sellmapper.getSellerById(sellerid);
      String user_pw = sellmapper.getSellerByPw(sellerpw);
      if(user != null) {
         System.out.println(user.getSellerpw());
         System.out.println(sellerpw);
         if(user_pw.equals(sellerpw)) {
            return true;
         }
      }
      return false;
   }

   @Override
   public String checkId(String sellerid) {
      // TODO Auto-generated method stub
      return null;
   }
   
   @Override
   public String getCategory(int snum) {
      return sellmapper.getCategory(snum);
   }
   
   @Override
      public StoreDTO getStoreBySellerid(String sellerid) {
         return storeMapper.getStoreBySellerId(sellerid);
      }

      @Override
      public List<SBoardDTO> getSBoardListBySellerid(String sellerid) {
         StoreDTO store = getStoreBySellerid(sellerid);
         int sNum = store.getSNum();
         return boardMapper.getSBoardListBySNum(sNum);
      }

      @Override
      public List<ProductDTO> getProductListBySellerid(String sellerid) {
         List<SBoardDTO> sBoardList = getSBoardListBySellerid(sellerid);
         List<String> sBoardnum_List = new ArrayList<>();
         
         for (SBoardDTO sBoard : sBoardList) {
             sBoardnum_List.add(sBoard.getSBoardnum() + "");
         }
         
         List<ProductDTO> productList = new ArrayList<>();
         for (String sBoardnum : sBoardnum_List ) {
            List<ProductDTO> ex = new ArrayList<>();
            ex = (productMapper.getListBySBoardnum(sBoardnum));
            
            for (ProductDTO product : ex) {
               productList.add(product);
            }
         }
         System.out.println(productList);
         return productList;
      }

      @Override
      public List<ProductDTO> getProductListBySelleridType(String sellerid, char type) {
         List<SBoardDTO> sBoardList = getSBoardListBySellerid(sellerid);
         List<String> sBoardnum_List = new ArrayList<>();
         for (SBoardDTO sBoard : sBoardList) {
             sBoardnum_List.add(sBoard.getSBoardnum() + "");
         }
         
         List<ProductDTO> productList = new ArrayList<>();
         for (String sBoardnum : sBoardnum_List ) {
            List<ProductDTO> ex = new ArrayList<>();
            ex = (productMapper.getListBySBoardnumType(sBoardnum, type));
            
            for (ProductDTO product : ex) {
               productList.add(product);
            }
         }
         return productList;
      }

      @Override
      public List<QnaDTO> getQnaListBySellerid(String sellerid) {
         List<ProductDTO> productList = getProductListBySellerid(sellerid);
      
         List<QnaDTO> QnaList = new ArrayList<>();
         
         for(ProductDTO product : productList) {
            List<QnaDTO> ex = new ArrayList<>();
            ex = qnaMapper.getQnaList(Integer.parseInt(product.getProductnum()));
            
            for (QnaDTO qna : ex) {
               QnaList.add(qna);
            }
         }
         return QnaList;
      }

      @Override
      public List<QnaDTO> getNoAnswerList(String sellerid) {
         List<ProductDTO> productList = getProductListBySellerid(sellerid);
         System.out.println(productList);
         
         List<QnaDTO> QnaList = new ArrayList<>();
         
         for(ProductDTO product : productList) {
            List<QnaDTO> ex = new ArrayList<>();
            System.out.println(product.getProductnum());
            ex = qnaMapper.getNoAnswerList(product.getProductnum());
            
            for (QnaDTO qna : ex) {
               QnaList.add(qna);
            }
         }
         return QnaList;
      }

      @Override
      public List<ReviewDTO> getReviewListBySellerid(String sellerid) {
         // apple의 store에 있는 모든 s_board들 가져옴
         List<SBoardDTO> sBoardList = getSBoardListBySellerid(sellerid);
         System.out.println("sBoardList : "+sBoardList);
         
         // 그 s_board들의 s_boardnum을 추출함
         List<String> sBoardnum_List = new ArrayList<>();
         for (SBoardDTO sBoard : sBoardList) {
             sBoardnum_List.add(sBoard.getSBoardnum() + "");
         }
         System.out.println("sBoardnum_List : "+sBoardnum_List);
         
         // s_boardnumr과 일치하는 ReviewDTO들을 모아서 List<ReviewDTO> 으로 보냄
         List<ReviewDTO> reviewList = new ArrayList<>();
         
         for (String sBoardnum : sBoardnum_List) {
            List<ReviewDTO> ex = new ArrayList<>();
            ex = (reviewMapper.getReviewListByConnectid(sBoardnum));
            
            for (ReviewDTO review : ex) {
               reviewList.add(review);
            }
         }
         System.out.println("reviewList : "+reviewList);
         
         return reviewList;
      }

   @Override
   public boolean checkSName(String sName) {
      return storeMapper.getStoreBySName(sName) == null;
   }

   @Override
   public boolean updateStore(StoreDTO store, String sellerid) {
      return storeMapper.updateStore(store, sellerid) == 1;
   }

   @Override
   public boolean updateStoreBackgroundPicture(MultipartFile[] files, String sellerid) throws Exception {
      StoreDTO store = getStoreBySellerid(sellerid);
      String s_num = store.getSNum()+"";
      
      if(files == null || files.length == 0) {
         return true;
      }
      else {
         //방금 등록한 게시글 번호
         boolean flag = false;
         System.out.println("파일 개수 : "+files.length);
         
         for(int i=0;i<files.length;i++) {
            System.out.println("for문 잘 들어옴.");
            MultipartFile file = files[i];
            System.out.println(file.getOriginalFilename());
            
            //apple.png
            String orgname = file.getOriginalFilename();
            //5
            int lastIdx = orgname.lastIndexOf(".");
            //.png
            String extension = orgname.substring(lastIdx);
            
            LocalDateTime now = LocalDateTime.now();
            String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

            //20240502162130141랜덤문자열.png
            String systemname = time+UUID.randomUUID().toString()+extension;
            
            //실제 생성될 파일의 경로
            //D:/0900_GB_JDS/7_spring/file/20240502162130141랜덤문자열.png
            String path = saveFolder+systemname;
            
            //File DB 저장
            FileDTO fdto = new FileDTO();
            fdto.setSystemname(systemname);
            fdto.setConnectionid(s_num);
            fdto.setType('S');
            System.out.println("fdto 잘 포장함."+fdto);
            
            flag = fmapper.insertThumbnail(fdto) == 1;
            System.out.println("DB에 fdto 잘 넣음.");
            
            //실제 파일 업로드
            file.transferTo(new File(path));
            System.out.println("실제 파일을 업로드 잘함.");
            
            if(!flag) {
               //업로드했던 파일 삭제, 게시글 데이터 삭제, 파일 data 삭제, ...
               System.out.println("flag false:업로드 실패");
               return false;
            }
         }
         System.out.println("flag ture:업로드 성공");
         return true;
      }
   }

   @Override
   public List<FileDTO> getFilesList() {
      List<FileDTO> filesList = fmapper.getFiles();
      return filesList;
   }

   @Override
   public List<Map<String, Object>> getMostReviewSBoard(String sellerid) {
      List<Map<String, Object>> result = new ArrayList<>();
      
      List<SBoardDTO> sBaordList = getSBoardListBySellerid(sellerid);
      char type = 'M';
      for (SBoardDTO sBoard : sBaordList) {
           String sBoardTitle = "";
           int sBoardnum = 0;
           int reviewCount = 0;
           FileDTO sBoardThumbnail = new FileDTO();
           
           sBoardTitle = sBoard.getSTitle();
           sBoardnum = sBoard.getSBoardnum();
           reviewCount = reviewMapper.getReviewCnt(sBoardnum);
           sBoardThumbnail = fmapper.getSBoardFile(sBoardnum);
           
           Map<String, Object> map = new HashMap<>();
           map.put("sBoardTitle", sBoardTitle);
           map.put("sBoardnum", sBoardnum);
           map.put("reviewCount", reviewCount);
           map.put("sBoardThumbnail", sBoardThumbnail);
           
           result.add(map);
      }
      // Sort the result list based on likeCount in descending order
       result.sort(new Comparator<Map<String, Object>>() {
           @Override
           public int compare(Map<String, Object> map1, Map<String, Object> map2) {
               Integer reviewCount1 = (Integer) map1.get("reviewCount");
               Integer reviewCount2 = (Integer) map2.get("reviewCount");
               return reviewCount2.compareTo(reviewCount1); // Descending order
           }
       });
       
       System.out.println("최고리뷰 결과: " + result);
      return result;
   }
   
   @Override
   public List<Map<String, Object>> getMostLikeSBoard(String sellerid) {
       List<Map<String, Object>> result = new ArrayList<>();

       List<SBoardDTO> sBaordList = getSBoardListBySellerid(sellerid);
       char type = 's';
       for (SBoardDTO sBoard : sBaordList) {
           String sBoardTitle = "";
           int sBoardnum = 0;
           int likeCount = 0;
           FileDTO sBoardThumbnail = new FileDTO();

           sBoardTitle = sBoard.getSTitle();
           sBoardnum = sBoard.getSBoardnum();
           likeCount = likeMapper.getLikeCount(sBoardnum, type);
           sBoardThumbnail = fmapper.getSBoardFile(sBoardnum);

           Map<String, Object> map = new HashMap<>();
           map.put("sBoardTitle", sBoardTitle);
           map.put("sBoardnum", sBoardnum);
           map.put("likeCount", likeCount);
           map.put("sBoardThumbnail", sBoardThumbnail);
           result.add(map);
       }

       // Sort the result list based on likeCount in descending order
       result.sort(new Comparator<Map<String, Object>>() {
           @Override
           public int compare(Map<String, Object> map1, Map<String, Object> map2) {
               Integer likeCount1 = (Integer) map1.get("likeCount");
               Integer likeCount2 = (Integer) map2.get("likeCount");
               return likeCount2.compareTo(likeCount1); // Descending order
           }
       });

       System.out.println("최고 좋아요 결과: " + result);

       return result;
   }

   @Override
   public List<Map<String, Object>> getHomeReview(String sellerid) {
      List<Map<String, Object>> result = new ArrayList<>();

       List<ReviewDTO> reviewList = getReviewListBySellerid(sellerid);
       
       for (ReviewDTO review : reviewList) {
           int sBoardnum = 0;
           FileDTO sBoardThumbnail = new FileDTO();
           
           sBoardnum = review.getConnectid();
           sBoardThumbnail = fmapper.getSBoardFile(sBoardnum);

           Map<String, Object> map = new HashMap<>();
           map.put("review", review);
           map.put("sBoardThumbnail", sBoardThumbnail);
           result.add(map);
       }
      
      return result;
   }

   @Override
   public List<Map<String, Object>> getTotalIncome(String sellerid) {
      List<Map<String, Object>> result = new ArrayList<>();
      
      List<SPaymentDTO> sPaymentList = paymentMapper.getLastSPaymentBySellerid(sellerid);
      
      int totalAmount = 0;
      int totalCost = 0;
      
      for(SPaymentDTO sPayment : sPaymentList) {
         totalAmount += sPayment.getAmount();
         totalCost += sPayment.getSCost();
      }
      
      Map<String, Object> map = new HashMap<>();
      map.put("totalCost", totalCost);
      map.put("totalAmount", totalAmount);
      result.add(map);
      
      return result;
   }
   
    @Override
      public List<QnaDTO> getQnaListBySelleridWithCri(Criteria cri, String sellerid) {
         List<ProductDTO> productList = getProductListBySellerid(sellerid);
         List<QnaDTO> QnaList = new ArrayList<>();

         for (ProductDTO product : productList) {
            List<QnaDTO> ex = new ArrayList<>();
            ex = qnaMapper.getQnaListWithCri(cri, Integer.parseInt(product.getProductnum()));
            for (QnaDTO qna : ex) {
               QnaList.add(qna);
            }
         }
         return QnaList;
      }
      @Override
      public List<QnaDTO> getNoAnswerQnaListBySelleridWithCri(Criteria cri, String sellerid) {
         List<ProductDTO> productList = getProductListBySellerid(sellerid);
         
         List<QnaDTO> QnaList = new ArrayList<>();
         
         for (ProductDTO product : productList) {
            List<QnaDTO> ex = new ArrayList<>();
            ex = qnaMapper.getNoAnswerQnaListWithCri(cri, Integer.parseInt(product.getProductnum()));
            
            for (QnaDTO qna : ex) {
               QnaList.add(qna);
            }
         }
         return QnaList;
      }

      @Override
      public long getQnaTotalBySellerid(String sellerid) {
         // s_num 찾기
         List<SBoardDTO> sBoardList = getSBoardListBySellerid(sellerid);
         System.out.println("sBoardList : " + sBoardList);

         // s_boardnum 찾기
         List<String> sBoardnum_List = new ArrayList<>();
         for (SBoardDTO sBoard : sBoardList) {
            sBoardnum_List.add(sBoard.getSBoardnum() + "");
         }
         System.out.println("sBoardnum_List : " + sBoardnum_List);

         long total = 0;

         for (String sBoardnum : sBoardnum_List) {
            // productnum 찾기
            List<ProductDTO> productList = productMapper.getListBySBoardnum(sBoardnum);
            for (ProductDTO product : productList) {
               String productnum = product.getProductnum();
               total += qnaMapper.getTotalByProductnum(productnum);
            }
         }
         return total;
      }

      @Override
      public long getNoAnswerQnaTotalBySellerid(String sellerid) {
         // s_num 찾기
         List<SBoardDTO> sBoardList = getSBoardListBySellerid(sellerid);
         System.out.println("sBoardList : " + sBoardList);

         // s_boardnum 찾기
         List<String> sBoardnum_List = new ArrayList<>();
         for (SBoardDTO sBoard : sBoardList) {
            sBoardnum_List.add(sBoard.getSBoardnum() + "");
         }
         System.out.println("sBoardnum_List : " + sBoardnum_List);

         long total = 0;

         for (String sBoardnum : sBoardnum_List) {
            // productnum 찾기
            List<ProductDTO> productList = productMapper.getListBySBoardnum(sBoardnum);
            for (ProductDTO product : productList) {
               String productnum = product.getProductnum();
               total += qnaMapper.getNoAnswerTotalByProductnum(productnum);
            }
         }
         return total;
      }

      @Override
      public long getReviewTotalBySellerid(String sellerid) {
      // s_num 찾기
            List<SBoardDTO> sBoardList = getSBoardListBySellerid(sellerid);
            System.out.println("sBoardList : " + sBoardList);

            long total = 0;

            // s_boardnum 찾기
            List<String> sBoardnum_List = new ArrayList<>();
            for (SBoardDTO sBoard : sBoardList) {
               total += reviewMapper.getTotalBySBoardnum(sBoard.getSBoardnum());
            }
            System.out.println("sBoardnum_List : " + sBoardnum_List);

            return total;
      }

      @Override
      public List<ReviewDTO> getReviewListBySelleridWithCri(Criteria cri, String sellerid) {
         // apple의 store에 있는 모든 s_board들 가져옴
         List<SBoardDTO> sBoardList = getSBoardListBySellerid(sellerid);
         System.out.println("sBoardList : " + sBoardList);

         // 그 s_board들의 s_boardnum을 추출함
         List<String> sBoardnum_List = new ArrayList<>();
         for (SBoardDTO sBoard : sBoardList) {
            sBoardnum_List.add(sBoard.getSBoardnum() + "");
         }
         System.out.println("sBoardnum_List : " + sBoardnum_List);

         // s_boardnumr과 일치하는 ReviewDTO들을 모아서 List<ReviewDTO> 으로 보냄
         List<ReviewDTO> reviewList = new ArrayList<>();

         for (String sBoardnum : sBoardnum_List) {
            List<ReviewDTO> ex = new ArrayList<>();
            ex = (reviewMapper.getReviewListBySBoardnumWithCri(cri, sBoardnum));

            for (ReviewDTO review : ex) {
               reviewList.add(review);
            }
         }
         System.out.println("reviewList : " + reviewList);

         return reviewList;
      }

	@Override
	public String getSelleridByStorename(String storename) {
		return sellmapper.getSelleridByStorename(storename);
	}

	@Override
	public List<ProductDTO> getCriSearchProduct(String text, String sellerid, Criteria cri) {
		List<ProductDTO> pList  = productMapper.getListByProductName(text, sellerid, cri);
		return pList;
	}

	@Override
	public String getSellerProfile(String sellerid) {
		System.out.println("아이디 "+sellerid);
		String src = "/summernoteImage/";
	    String systemname = fmapper.getSellerProfileById(sellerid);
	    System.out.println("너누구냐 "+systemname);
	    systemname = src+systemname;
		return systemname;
	}
	
	//페이징처리
	@Override
	public int getTotal(Criteria cri, String sellerid) {
		return productMapper.getTotal(cri, sellerid);
	}
	
	//MDM
		@Override
		public char checkStoreSignup(String sellerid) {
			return srMapper.checkStoreSignup(sellerid);
		}
		//MDM
		@Override
		public boolean storeInfoCheck(String sellerid) {
			return storeMapper.storeInfoCheck(sellerid) == 1;
		}

		@Override
		public List<ProductDTO> getProductCriList(String sellerid, Criteria cri) {
			return productMapper.getProductCriList(sellerid ,cri);
		}

		@Override
		public long getTotalCnt(String sellerid, String text) {
			return productMapper.getTotalCnt(sellerid, text);
		}
}
