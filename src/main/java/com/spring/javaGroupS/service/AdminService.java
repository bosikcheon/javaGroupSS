package com.spring.javaGroupS.service;

public interface AdminService {

	int setMemberLevelChange(int idx, int level);

	String setMemberLevelSelectCheck(int levelSelect, String idxSelectArray);

}
