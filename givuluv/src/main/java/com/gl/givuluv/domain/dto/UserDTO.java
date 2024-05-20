package com.gl.givuluv.domain.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class UserDTO {
	private String userid;
	private String userpw;
	private String username;
	private char gender;
	private String birth;
	private String email;
	private String userphone;
	private String nickname;
	private String zipcode;
	private String addr;
	private String addrdetail;
	private String addretc;
	private Date regdate;
	private int bonus;
	private char type;
	private String usercategory;
}
