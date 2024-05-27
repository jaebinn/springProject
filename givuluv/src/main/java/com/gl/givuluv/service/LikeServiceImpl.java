package com.gl.givuluv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.LikeDTO;
import com.gl.givuluv.mapper.LikeMapper;

@Service
public class LikeServiceImpl implements LikeService{

	@Autowired
	private LikeMapper lmapper;
	
	@Override
	public boolean addLike(LikeDTO like) {
		return lmapper.insertLike(like) == 1;
	}

	@Override
	public int likeCount(LikeDTO like) {
		return lmapper.likeCount(like);
	}

	@Override
	public LikeDTO getLike(LikeDTO like) {
		return lmapper.getLike(like);
	}

	@Override
	public boolean cancelLike(int likenum) {
		return lmapper.deleteLike(likenum) == 1;
	}

}
