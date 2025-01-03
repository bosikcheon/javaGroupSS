package com.spring.javaGroupS.vo;

import lombok.Data;

@Data
public class SurveyQuestionContentVO {
	private int idx;
	private int sIdx;
	private int qIdx;
	private String acontent;
	private int realAnswerCnt;
}
