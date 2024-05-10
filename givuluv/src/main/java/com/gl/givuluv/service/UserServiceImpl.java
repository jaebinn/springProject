package com.gl.givuluv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.UserDTO;
import com.gl.givuluv.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper umapper;
	
	@Override
	public boolean join(UserDTO user) {
		return umapper.insertUser(user) == 1;
	}

	public boolean login(String userid, String userpw) {
		UserDTO user = umapper.getUserById(userid);
		if(user != null) {
			if(user.getUserpw().equals(userpw)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkId(String userid) {
		return umapper.getUserById(userid) == null;
	}

}
