package com.gl.givuluv.service;

import java.util.List;

import com.gl.givuluv.domain.dto.CommentDTO;
import com.gl.givuluv.domain.dto.CommentPageDTO;
import com.gl.givuluv.domain.dto.LikeDTO;

public interface CommentService {
	/**
	 * 댓글 등록 (반환 : CommentDTO)
	 * @param comment
	 * @return
	 */
	CommentDTO registComment(CommentDTO comment);
	
	/**
	 * 공백만 있다면 true 반환
	 * @param commentdetail
	 * @return
	 */
	boolean commentSpaceCheck(String commentdetail);
	
	/**
	 * 해당 캠페인 번호의 댓글들 찾기 (desc)
	 * commentlastnum : 불러와야하는 댓글 번호의 마지막 번호
	 * amount : 불러오고 싶은 댓글들
	 * @param cBoardnum
	 * @param commentlastnum
	 * @param amount
	 * @return
	 */
	List<CommentDTO> getComments(int cBoardnum, int commentlastnum, int amount);
	
	/**
	 * 해당 댓글의 좋아요 수
	 * @param comment
	 * @return
	 */
	int getCommentLikeCount(CommentDTO comment);
	
	/**
	 * 댓글에 해당하는 댓글과 'loginUser'의 좋아요 객체
	 * @param comments
	 * @param loginUser
	 * @return
	 */
	LikeDTO getCommentLike(CommentDTO comment, String loginUser);
	
	/**
	 * 캠페인 댓글에 해당하는 단체 프로필 링크 찾기 (user 댓글은 null)
	 * @param comments
	 * @return
	 */
	String getCommentProfileFileLink(CommentDTO comment);
	
	/**
	 * comment 작성자 이름 리스트 찾기
	 * @param comments
	 * @return
	 */
	String getCommentWriterName(CommentDTO comment);
	
	/**
	 * 캠페인 댓글 및 댓글 관련 DTO 리스트
	 * @param cBoardnum
	 * @param commentlastnum
	 * @param loginUser
	 * @return
	 */
	List<CommentPageDTO> getCommentList(int cBoardnum, int commentlastnum, String loginUser);
	
	/**
	 * 해당 comment의 내용 수정 후, 수정된 comment 객체 반환
	 * @param comment
	 * @return
	 */
	CommentDTO modify(CommentDTO comment);
	
	/**
	 * 해당 comment 삭제
	 * @param commentnum
	 * @return
	 */
	boolean remove(int commentnum);
}
