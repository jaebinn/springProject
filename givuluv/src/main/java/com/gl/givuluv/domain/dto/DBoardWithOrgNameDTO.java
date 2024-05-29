package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class DBoardWithOrgNameDTO {
	private DBoardDTO dBoard;
	private String orgname;
	private String systemname;

	public DBoardWithOrgNameDTO(DBoardDTO dBoard, String orgname) {
		this.dBoard = dBoard;
		this.orgname = orgname;
	}
	public DBoardWithOrgNameDTO(DBoardDTO dBoard, String orgname, String systemname) {
		this.dBoard = dBoard;
		this.orgname = orgname;
		this.systemname = systemname;
	}
}
