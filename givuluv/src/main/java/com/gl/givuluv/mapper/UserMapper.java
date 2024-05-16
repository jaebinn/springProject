package com.gl.givuluv.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.UserDTO;

@Mapper
public interface UserMapper {

	//C
	int insertUser(UserDTO user);
	//R
	UserDTO getUserById(String userid);
	//U
	int updateUser(UserDTO user);
	//D
	int deleteUser(String userid);
	//닉네임 유무
	UserDTO getUserByNickname(String nickname);
	//아이디 찾기
	UserDTO getUseridByEmail(String email);
	//비밀번호 찾기
	boolean nickAndIdCheck(String nickname, String userid);
}
