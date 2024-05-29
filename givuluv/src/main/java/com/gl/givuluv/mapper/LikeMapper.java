package com.gl.givuluv.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.LikeDTO;

@Mapper
public interface LikeMapper {
	int insertLike(LikeDTO like);
	int likeCount(LikeDTO like);
	LikeDTO getLike(LikeDTO like);
	int deleteLike(int likenum);
}
