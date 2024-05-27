package com.gl.givuluv.service;

import com.gl.givuluv.domain.dto.LikeDTO;

public interface LikeService {
	boolean addLike(LikeDTO like);
	
	int likeCount(LikeDTO like);
	LikeDTO getLike(LikeDTO like);
	
	boolean cancelLike(int likenum);
}
