package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class DBoardWithFile {
	private DBoardDTO dBoard;
	private int deadline;
	private double percentage;
	private String systemname;
	
	public DBoardWithFile(DBoardDTO dBoard, int deadline, double percentage, String systemname) {
		this.dBoard = dBoard;
		this.deadline = deadline;
		this.percentage = percentage;
		this.systemname = systemname;
	}
}
