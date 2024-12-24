package com.spring.javaGroupS.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@Data
public class User2VO {
	private int idx;

	@NotEmpty(message = "아이디는 공백을 허용하지 않습니다.")
	@Size(min=3, max=30, message = "아이디는 3~30 글자를 허용합니다.")
	private String mid;
	
	@NotEmpty(message = "비밀번호는 공백을 허용하지 않습니다.")
	@Size(min=2, max=30, message = "비밀번호는 2~30 글자를 허용합니다.")
	private String pwd;
	
	@NotEmpty(message = "성명은 공백을 허용하지 않습니다.")
	@Size(min=2, max=30, message = "성명은 2~30 글자를 허용합니다.")
	private String name;

	//@NotEmpty(message = "나이는 숫자만 입력하세요.")
	@Range(min=19, max=99, message = "나이는 19~99살 까지만 허용합니다.")
	private int age;
	
	
	private String gender;
	
	@Size(min=2, max=10, message = "주소는 2~10 글자를 허용합니다.")
	private String address;
	
	@Size(min=2, max=10, message = "직업은 2~10 글자를 허용합니다.")
	private String job;
}
