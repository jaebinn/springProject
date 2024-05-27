package com.gl.givuluv.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.ReviewDTO;
import com.gl.givuluv.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewMapper rmapper;
	
	@Override
	public ReviewDTO regist(ReviewDTO review) {
		if(rmapper.insertDReview(review) == 1) {
			return rmapper.getLastReview(review.getUserid());
		}
		return null;
	}

	@Override
	public List<ReviewDTO> getReviewList(int dBoardnum) {
		return rmapper.getReviewList(dBoardnum);
	}

	@Override
	public int getReviewCnt(int dBoardnum) {
		return rmapper.getReviewCnt(dBoardnum);
	}

	@Override
	public boolean deleteReview(int reviewnum) {
		return rmapper.deleteReview(reviewnum);
	}

	@Override
	public boolean updateReview(int reviewnum, String reviewdetail) {
		return rmapper.updateReview(reviewnum, reviewdetail);
	}

}
