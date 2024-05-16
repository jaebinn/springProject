package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class DBoardDTO {
	private String dBoardnum;
	private String dTitle;
	private String dContent;
	private String targetAmount;
	private String saveMoney;
	private String dRegdate;
	private String dEnddate;
	private String orgid;
}
