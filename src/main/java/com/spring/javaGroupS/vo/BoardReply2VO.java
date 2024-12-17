package com.spring.javaGroupS.vo;

import lombok.Data;

@Data
public class BoardReply2VO {
	private int idx;
	private int boardIdx;
	private int re_step;
	private int re_order;
	private String mid;
	private String nickName;
	private String content;
	private String hostIp;
	private String wDate;
}
