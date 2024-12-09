package com.spring.javaGroupS.vo;

import lombok.Data;

//@Getter
//@Setter
//@ToString
@Data
public class UserVO {
	private int idx;
	private String mid;
	private String pwd;
	private String name;
	private int age;
	private String gender;
	private String address;

	private String nickName;
}
