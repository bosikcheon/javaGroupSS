package com.spring.javaGroupS.vo;

import lombok.Data;

@Data
public class BoardVO {
	private int idx;
	private String mid;
	private String nickName;
	private String title;
	private String content;
	private String hostIp;
	private String openSw;
	private int readNum;
	private int good;
	private String wDate;
	private String claim;
	
	private int date_diff;	// 작성된 게시물의 날짜 비교값
	private int time_diff;	// 작성된 게시물의 시간 비교값
	private int replyCnt;		// 댓글의 개수
	
}
