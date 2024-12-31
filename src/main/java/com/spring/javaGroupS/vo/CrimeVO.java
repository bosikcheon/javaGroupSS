package com.spring.javaGroupS.vo;

import lombok.Data;

@Data
public class CrimeVO {
	private int idx;
	private int year;
	private String police;
	private int murder;
	private int robbery;
	private int theft;
	private int violence;
	
	private int totMurder;
	private int totRobbery;
	private int totTheft;
	private int totViolence;
	private double avgMurder;
	private double avgRobbery;
	private double avgTheft;
	private double avgViolence;
}
