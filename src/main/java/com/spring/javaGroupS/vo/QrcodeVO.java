package com.spring.javaGroupS.vo;

import lombok.Data;

@Data
public class QrcodeVO {
	
	// 개인 정보를 담을 필드
	private String mid;
	private String name;
	private String email;
	
	
	// 소개사이트에 필요한 필드
	private String subject;
	private String moveUrl;
	
	// 영화 티켓정보를 담는 필드
	private String movieCinema;
	private String movieName;
	private String movieDate;
	private String movieTime;
	private int movieAdult;
	private int movieChild;
	
}
