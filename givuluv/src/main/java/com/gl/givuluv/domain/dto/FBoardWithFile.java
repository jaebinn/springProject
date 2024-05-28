package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class FBoardWithFile {
	private FBoardDTO fBoard;
	private int deadline;
	private double percentage;
	private String systemname;
	
	public FBoardWithFile(FBoardDTO fBoard, int deadline, double percentage, String systemname) {
		this.fBoard = fBoard;
		this.deadline = deadline;
		this.percentage = percentage;
		this.systemname = systemname;
	}
}
