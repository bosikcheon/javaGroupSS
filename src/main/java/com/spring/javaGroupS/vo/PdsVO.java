package com.spring.javaGroupS.vo;

import lombok.Data;

@Data
public class PdsVO {
	private int idx;
	private String mid;
	private String nickName;
	private String fName;
	private String fSName;
	private int fSize;
	private String part;
	private String title;
	private String content;
	private String openSw;
	private String hostIp;
	private String pwd;
	private String fDate;
	private int downNum;
	
	private int date_diff;
	private int hour_diff;
}
