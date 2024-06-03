package com.gl.givuluv.domain.dto;

import lombok.Data;

@Data
public class OrgapproveDTO {
	private int oApprovenum;
	private String instituteName;
    private String address;
    private String phoneNumber;
    private String information;
    private String regdate;
    private String orgid;
    private char isagree;
}
