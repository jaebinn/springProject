package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.ReviewDTO;

@Mapper
public interface ReviewMapper {
	int insertDReview(ReviewDTO review);

	ReviewDTO getLastReview(String userid);

	List<ReviewDTO> getReviewList(int dBoardnum);

	int getReviewCnt(int dBoardnum);

	boolean deleteReview(int reviewnum);

	boolean updateReview(int reviewnum, String reviewdetail);

	List<ReviewDTO> getDReviewThree();
	
	List<ReviewDTO> getProductReviewList(int sBoardnum);
	
	 List<ReviewDTO> getReviewListByConnectid(String connectid);


}
