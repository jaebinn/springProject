package com.gl.givuluv.service;

import java.util.List;

import com.gl.givuluv.domain.dto.ReviewDTO;

public interface ReviewService {

	ReviewDTO regist(ReviewDTO review);

	List<ReviewDTO> getReviewList(int dBoardnum);

	int getReviewCnt(int dBoardnum);

	boolean deleteReview(int reviewnum);

	boolean updateReview(int reviewnum, String reviewdetail);

}
