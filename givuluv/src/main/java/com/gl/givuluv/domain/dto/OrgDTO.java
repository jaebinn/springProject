package com.gl.givuluv.domain.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class OrgDTO {
	private String orgid;
	private String orgpw;
	private String orgname;
	private String logo;
	private String orgphone;
	private String ceoname;
	private String orgzipcode;
	private String orgaddr;
	private String orgaddrdetail;
	private String orgaddretc;
}
