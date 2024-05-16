package com.gl.givuluv.service;


import com.gl.givuluv.domain.dto.UserDTO;

public interface UserService {
	boolean join(UserDTO user);
	boolean login(String userid, String userpw);
	boolean checkId(String userid);
	boolean checkNickname(String nickname);
	boolean checkNickAndEmail(String nickname, String email);
	UserDTO getUseridByEmail(String email);
	boolean nickAndIdCheck(String nickname, String userid);
	UserDTO getUserById(String userid);
}
