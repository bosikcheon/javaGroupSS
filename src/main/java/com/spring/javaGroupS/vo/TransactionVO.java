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
public class TransactionVO {
	private int idx;
	
	@NotEmpty(message = "아이디는 공백을 허용하지 않습니다./midEmpty")
	@Size(min=3, max=30, message = "아이디는 3~30 글자를 허용합니다./midSizeOut")
	private String mid;
	
	@NotEmpty
	@Size(min=2, max=30, message = "비밀번호는 2~30 글자를 허용합니다./pwdSizeOut")
	private String pwd;
	
	@NotEmpty
	@Size(min=2, max=30, message = "성명은 2~30 글자를 허용합니다./nameSizeOut")
	private String name;

	@Range(min=19, max=99, message = "나이는 19~99 글자를 허용합니다./ageRangeOut")
	private int age;
	
	
	private String gender;
	
	@Size(min=2, max=10, message = "주소는 2~10 글자를 허용합니다./addressSizeOut")
	private String address;
}
