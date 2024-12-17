package com.spring.javaGroupS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaGroupS.dao.AdminDAO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDAO adminDAO;

	@Override
	public int setMemberLevelChange(int idx, int level) {
		return adminDAO.setMemberLevelChange(idx, level);
	}

	@Override
	public String setMemberLevelSelectCheck(int levelSelect, String idxSelectArray) {
		String res = "0";
		String[] idxSelectArrays = idxSelectArray.split("/");
		
		for(String idx : idxSelectArrays) {
			adminDAO.setMemberLevelChange(Integer.parseInt(idx), levelSelect);
			res = "1";
		}
		
		return res;
	}
	
}
