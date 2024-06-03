package com.gl.givuluv.service;

import java.util.List;

import com.gl.givuluv.domain.dto.CBoardDTO;
import com.gl.givuluv.domain.dto.FollowDTO;
import com.gl.givuluv.domain.dto.LikeDTO;

public interface CBoardService {
	/**
	 * 캠페인 게시글과 게시글 안에 있는 이미지 파일들을 DB에 저장
	 * @param cboard
	 * @param filenames
	 * @return
	 */
	boolean regist(CBoardDTO cboard, String filenames);
	
	/**
	 * orgid 또는 manager의 ID로 캠페인 게시글의 마지막 번호 찾기
	 * @param connectid
	 * @return
	 */
	int getCampaignLastNumByConnectid(String connectid);
	
	/**
	 * 캠페인 게시글의 번호로 캠페인 객체 찾기
	 * @param cBoardnum
	 * @return
	 */
	CBoardDTO getCampaign(int cBoardnum);
	
	/**
	 * 유저의 카테고리 알고리즘 게시글 가져오기
	 * @param loginUser
	 * @return
	 */
	List<CBoardDTO>  getCampaignBoardListOfUser(String loginUser, int cBoardnum, int amount);
	
	/**
	 * 캠페인에 해당하는 단체의 프로필 링크 찾기
	 * @param orgid
	 * @return
	 */
	String getCampaignProfileFileLink(CBoardDTO cboard);
	
	/**
	 * 캠페인 글쓴이 이름(매니저는 null 반환)
	 * @param cboard
	 * @return
	 */
	String getCampaignWriterName(CBoardDTO cboard);
	
	/**
	 * 캠페인 글을 작성한 단체와 session loginUser의 팔로우 객체 찾기
	 * @param cboard
	 * @param loginUser
	 * @return
	 */
	FollowDTO getfollowOfCampaign(CBoardDTO cboard, String loginUser);
	/**
	 * 해당 번호의 캠페인 글을 session loginUser가 좋아요를 눌렀는지 객체 찾기 
	 * @param cBoardnum
	 * @param loginUser
	 * @return
	 */
	LikeDTO getCampaignLike(int cBoardnum, String loginUser);

	/**
	 * 해당 번호의 캠페인 글의 좋아요 갯수
	 * @param cBoardnum
	 * @return
	 */
	int getlikeCount(int cBoardnum);
	/**
	 * 해당 캠페인을 쓴 단체의 카테고리 찾기
	 * @param cboard
	 * @return
	 */
	String getCategory(CBoardDTO cboard);
	
	boolean insertLike(LikeDTO like);
	boolean cancelLike(LikeDTO like);

	List<CBoardDTO> getCampaignList(int boardlastnum, int amount);
}
