package com.spring.javaGroupS.vo;

import lombok.Data;

@Data
public class SurveyRealAnswerVO {
	private int idx;
	private int bIdx;
	private int sIdx;
	private int qIdx;
	private int aIdx;
	private String detailAnswer;
}
