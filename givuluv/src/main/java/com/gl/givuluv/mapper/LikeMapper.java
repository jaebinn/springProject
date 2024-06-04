package com.gl.givuluv.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.LikeDTO;

@Mapper
public interface LikeMapper {
	int insertLike(LikeDTO like);
	int likeCount(LikeDTO like);
	LikeDTO getLike(LikeDTO like);
	int deleteLike(int likenum);
	List<LikeDTO> getLikeListBySBoardnum(String sBoardnum);
	//MDM
	boolean deleteSLike(int sboardnum, String userid);
	//MDM
	LikeDTO getSBoardLike(int connectid, String loginUser);
	int deleteByLikeDTO(LikeDTO like);
	int getLikeCount(int sBoardnum, char type);
}
