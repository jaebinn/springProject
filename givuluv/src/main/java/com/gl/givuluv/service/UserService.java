package com.gl.givuluv.service;


import com.gl.givuluv.domain.dto.UserDTO;

public interface UserService {
	boolean join(UserDTO user);
	boolean login(String userid, String userpw);
	boolean checkId(String userid);
}
