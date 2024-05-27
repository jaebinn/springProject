package com.gl.givuluv.domain.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class DBoardDTO {
	private int dBoardnum;
	private String dTitle;
	private String dContent;
	private int targetAmount;
	private int saveMoney;
	private String dRegdate;
	private String dEnddate;
	private String orgid;
	
	// dRegdate 필드에 대한 getter 메서드
    public Date getDRegdate() {
        Date regdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            regdate = formatter.parse(dRegdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return regdate;
    }
}
