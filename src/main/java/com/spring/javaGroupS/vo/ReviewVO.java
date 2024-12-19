package com.spring.javaGroupS.vo;

import lombok.Data;

@Data
public class ReviewVO {
	private int idx;
	private String part;
	private int partIdx;
	private String mid;
	private String nickName;
	private String content;
	private int star;
	private String reviewDate;
	
	// reviewReply테이블
	private int replyIdx;
	private int reviewIdx;
	private String replyMid;
	private String replyNickName;
	private String replyContent;
	private String replyDate;
}
