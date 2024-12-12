package com.spring.javaGroupS.vo;

import lombok.Data;

@Data
public class MemberVO {
	private int idx;
	private String mid;
	private String pwd;
	private String nickName;
	private String name;
	private String gender;
	private String birthday;
	private String tel;
	private String address;
	private String email;
	private String content;
	private String photo;
	private int level;
	private String userInfor;
	private String userDel;
	private int point;
	private int visitCnt;
	private int todayCnt;
	private String startDate;
	private String lastDate;
	
	private String tempMid;	  // 아이디 중복체크를위한 임시 아이디
	private int elapsed_date;	// 최종접속 경과일(탈퇴시 사용)
	private String strLevel;	// 회원등급명
	//private String strUserDel; // OK:탈퇴신청 , NO:활동중
	
	private String tel1;
	private String tel2;
	private String tel3;
	private String email1;
	private String email2;
	private String postcode;
	private String address2;
	private String detailAddress;
	private String extraAddress;
}
