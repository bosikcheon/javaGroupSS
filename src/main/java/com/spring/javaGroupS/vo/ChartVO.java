package com.spring.javaGroupS.vo;

import lombok.Data;

@Data
public class ChartVO {
	private String title;
	private String subTitle;
	
	private String legend1;
	private String legend2;
	private String legend3;
	
	private String x1;
	private String x2;
	private String x3;
	private String x4;
	
	private String x1Value1;
	private String x1Value2;
	private String x1Value3;
	private String x2Value1;
	private String x2Value2;
	private String x2Value3;
	private String x3Value1;
	private String x3Value2;
	private String x3Value3;
	private String x4Value1;
	private String x4Value2;
	private String x4Value3;
	
	// 최근 방문자수를 담기위한 추가필드
	private String xTitle;
	private String regend;
	private String visitDate;
	private int visitCount;
}
