package com.spring.javaGroupS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javaGroupS.dao.UserDAO;
import com.spring.javaGroupS.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public List<UserVO> getUserList() {
		return userDAO.getUserList();
	}

	@Override
	public int setUserDeleteOk(int idx) {
		return userDAO.setUserDeleteOk(idx);
	}

	@Override
	public UserVO getUserIdxSearch(int idx) {
		return userDAO.getUserIdxSearch(idx);
	}

	@Override
	public int getUserUpdateOk(UserVO vo) {
		return userDAO.getUserUpdateOk(vo);
	}

	@Override
	public UserVO getUserIdSearch(String mid) {
		return userDAO.getUserIdSearch(mid);
	}

	@Override
	public int setUserInputOk(UserVO vo) {
		return userDAO.setUserInputOk(vo);
	}

	@Override
	public int getUserCnt() {
		return userDAO.getUserCnt();
	}

	@Override
	public List<UserVO> getUserSearchList(String mid) {
		return userDAO.getUserSearchList(mid);
	}


}
