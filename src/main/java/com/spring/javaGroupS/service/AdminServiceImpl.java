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
	
}
