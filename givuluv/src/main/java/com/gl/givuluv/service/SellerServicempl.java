package com.gl.givuluv.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.QnaDTO;
import com.gl.givuluv.domain.dto.ReviewDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SellerDTO;
import com.gl.givuluv.domain.dto.StoreDTO;
import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.mapper.BoardMapper;
import com.gl.givuluv.mapper.FileMapper;
import com.gl.givuluv.mapper.ProductMapper;
import com.gl.givuluv.mapper.QnaMapper;
import com.gl.givuluv.mapper.ReviewMapper;
import com.gl.givuluv.mapper.SellerMapper;
import com.gl.givuluv.mapper.StoreMapper;

@Service
public class SellerServicempl implements SellerService{
	
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
	private FileMapper fileMapper;
	@Override
	public boolean join(SellerDTO seller) {
		
		return sellmapper.insertSeller(seller) == 1;
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
	
//	boolean 타입이 아님 pw를 받아와야 함
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

}
