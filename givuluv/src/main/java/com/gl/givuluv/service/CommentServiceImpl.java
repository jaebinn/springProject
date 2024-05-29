package com.gl.givuluv.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.CommentDTO;
import com.gl.givuluv.domain.dto.CommentPageDTO;
import com.gl.givuluv.domain.dto.FileDTO;
import com.gl.givuluv.domain.dto.LikeDTO;
import com.gl.givuluv.mapper.CommentMapper;
import com.gl.givuluv.mapper.FileMapper;
import com.gl.givuluv.mapper.LikeMapper;
import com.gl.givuluv.mapper.OrgMapper;
import com.gl.givuluv.mapper.UserMapper;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentMapper cmmapper;
	
	@Autowired
	private LikeMapper lmapper;
	
	@Autowired
	private FileMapper fmapper;
	
	@Autowired
	private OrgMapper omapper;
	
	@Autowired
	private UserMapper umapper;
	
	@Override
	public boolean registComment(CommentDTO comment) {
		return cmmapper.insertComment(comment) == 1;
	}

	@Override
	public List<CommentDTO> getComments(int cBoardnum, int commentlastnum, int amount) {
		if(commentlastnum == 0 ) {
			commentlastnum = cmmapper.getCommentLastNum();
		}
		return cmmapper.getComments(cBoardnum, commentlastnum, 10);
	}

	@Override
	public int getCommentLikeCount(CommentDTO comment) {
		LikeDTO like = new LikeDTO();
		like.setConnectid(comment.getCommentnum());
		like.setType('R');
		return lmapper.likeCount(like);
	}
	
	@Override
	public LikeDTO getCommentLike(CommentDTO comment, String loginUser) {
		LikeDTO like = new LikeDTO();
		like.setConnectid(comment.getCommentnum());
		like.setType('R');
		like.setUserid(loginUser);
		return lmapper.getLike(like);
	}
	
	@Override
	public String getCommentProfileFileLink(CommentDTO comment) {
		FileDTO file = new FileDTO();
		if(comment.getType()=='O') {
			FileDTO temp = new FileDTO();
			temp.setType('O');
			temp.setConnectionid(comment.getConnectid());
			file = fmapper.getFile(temp);
		}
		else {
			return null;
		}
		return "/summernoteImage/"+file.getSystemname();
	}
	
	@Override
	public String getCommentWriterName(CommentDTO comment) {
		if(comment.getType()=='O') {
			// orgname
			return omapper.getOrgById(comment.getConnectid()).getOrgname();
		}
		//nickname
		return umapper.getUserById(comment.getConnectid()).getNickname();
	}
	
	

	@Override
	public List<CommentPageDTO> getCommentList(int cBoardnum, int commentlastnum, String loginUser) {
		List<CommentPageDTO> commentPageList = new ArrayList<CommentPageDTO>();
		// 댓글들
		List<CommentDTO> comments = getComments(cBoardnum, commentlastnum, 10);
		System.out.println("댓글들 : "+ comments);
		
		
		for (CommentDTO comment : comments) {
			System.out.println("댓글 번호 : "+comment.getCommentnum());
			CommentPageDTO cp = new CommentPageDTO();
			// 댓글 프로필 파일들
			String commentFileLink = getCommentProfileFileLink(comment);
			System.out.println("댓글 프로필 파일 링크 : "+commentFileLink);
			// 댓글 작성자 닉네임 (단체는 orgname)
			String commentWriterName = getCommentWriterName(comment);
			System.out.println("작성자 : "+commentWriterName);
			// 댓글 좋아요 수
			int commentLikeCount = getCommentLikeCount(comment);
			System.out.println("좋아요 수 : "+commentLikeCount);
			if(loginUser != null) {
				// '나'의 댓글들 좋아요 객체 리스트
				LikeDTO commentLike = getCommentLike(comment, loginUser);
				System.out.println("댓글 좋아요 : "+commentLike);
				
				cp.setCommentLike(commentLike);
			}
			cp.setComment(comment);
			cp.setCommentLikeCount(commentLikeCount);
			cp.setCommentFileLink(commentFileLink);
			cp.setCommentWriterName(commentWriterName);
			commentPageList.add(cp);
		}
		return commentPageList;
	}
}
