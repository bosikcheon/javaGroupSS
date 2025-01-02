package com.spring.javaGroupS.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class JavaGroupProvide {
	
	// 파일명에 지정된 자리수만큼 난수를 발생시켜서 새로운 파일명을 만들어 반환하는 메소드
	public String newNameCreate(int len) {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String newName = sdf.format(today);
		newName += RandomStringUtils.randomAlphanumeric(len) + "_";
		return newName;
	}
	
}
