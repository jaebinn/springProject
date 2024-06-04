package com.gl.givuluv.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.Criteria;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.ProductDTO;
import com.gl.givuluv.domain.dto.QnaDTO;
import com.gl.givuluv.domain.dto.ReviewDTO;
import com.gl.givuluv.domain.dto.SBoardDTO;
import com.gl.givuluv.domain.dto.SellerDTO;
import com.gl.givuluv.domain.dto.StoreDTO;

public interface SellerService {

//   회원가입 
   boolean join(SellerDTO seller);
//   로그인
   boolean login(String sellerid, String sellerpw);
//   이메일로 아이디 찾기
   boolean checkIdAndEmail(String sellerid, String email);
//   아이디 중복확인
   boolean checkId_duplication(String sellerid);
   
//   로그인(비밀번호 확인)
   String checkPw(String sellerpw);
//   로그인(아이디 확인)
   String checkId(String sellerid);
   
   String getCategory(int snum);
   
   List<ProductDTO> getProductListBySellerid(String sBoardnum);

   List<QnaDTO> getQnaListBySellerid(String sellerid);

   List<QnaDTO> getNoAnswerList(String sellerid);

   List<SBoardDTO> getSBoardListBySellerid(String sellerid);

   List<ProductDTO> getProductListBySelleridType(String sellerid, char type);

   List<ReviewDTO> getReviewListBySellerid(String sellerid);
   
   StoreDTO getStoreBySellerid(String sellerid);
   
   boolean checkSName(String sName);
   
   boolean updateStore(StoreDTO store, String sellerid);
   boolean updateStoreBackgroundPicture(MultipartFile[] files, String sellerid) throws Exception ;
   List<FileDTO> getFilesList();
   List<Map<String, Object>> getMostReviewSBoard(String sellerid);
   List<Map<String, Object>> getMostLikeSBoard(String sellerid);
   List<Map<String, Object>> getHomeReview(String sellerid);
   List<Map<String, Object>> getTotalIncome(String sellerid);
   
   List<QnaDTO> getQnaListBySelleridWithCri(Criteria cri, String sellerid);
      
      List<QnaDTO> getNoAnswerQnaListBySelleridWithCri(Criteria cri, String sellerid);

      //seller의 총 qna
      long getQnaTotalBySellerid(String sellerid);
      //seller의 총 답변 안 된 qna
      long getNoAnswerQnaTotalBySellerid(String sellerid);
      //seller의 총 review
      long getReviewTotalBySellerid(String sellerid);
   List<ReviewDTO> getReviewListBySelleridWithCri(Criteria cri, String sellerid);
   
   String getSelleridByStorename(String storename);

}
